package com.backend.users.Security.Auth.Service;


import com.auth0.jwt.interfaces.Verification;
import com.backend.users.Adress.Domain.Adress;
import com.backend.users.Adress.Infrastructure.AdressRepository;
import com.backend.users.Confirmation.Domain.Confirmation;
import com.backend.users.Confirmation.Infrastructure.ConfirmationRepository;
import com.backend.users.Security.Auth.DTOs.*;
import com.backend.users.Security.Exceptions.UnauthorizedException;
import com.backend.users.Security.Exceptions.UserAlreadyExists;
import com.backend.users.Security.JWT.JwtService;
import com.backend.users.Users.Domain.User;
import com.backend.users.Users.Domain.UserService;
import com.backend.users.Users.Infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ApplicationEventPublisher eventPublisher;
    private final UserService userService;
    private final ConfirmationRepository confirmationRepository;
    private final AdressRepository adressRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService
            , ApplicationEventPublisher eventPublisher, UserService userService, ConfirmationRepository confirmationRepository, AdressRepository adressRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.eventPublisher = eventPublisher;
        this.userService = userService;
        this.confirmationRepository = confirmationRepository;
        this.adressRepository = adressRepository;
    }

    public ResponseSignupDTO signup(SignupDTO dto) throws BadRequestException, UserAlreadyExists {
        Optional<User> user = userRepository.findByEmail(dto.getEmail());
        if(user == null){
            if(!validateEmail(dto.getEmail())){
                throw new BadRequestException("Email is not valid");
            }
            User u = new User();
            u.setEmail(dto.getEmail());
            u.setPassword(passwordEncoder.encode(dto.getPassword()));
            u.setFirstName(dto.getFirstName());
            u.setLastName(dto.getLastName());
            u.setPhoneNumber(String.valueOf(dto.getPhoneNumber()));
            User save = userRepository.save(u);
            ResponseSignupDTO responseSignupDTO = new ResponseSignupDTO();
            responseSignupDTO.setEmail(save.getEmail());
            responseSignupDTO.setFirstName(save.getFirstName());
            responseSignupDTO.setLastName(save.getLastName());
            responseSignupDTO.setId(save.getId());
            Confirmation confirmation = new Confirmation();
            confirmation.setUserId(save.getId());
            confirmation.setConfirmationDate(ZonedDateTime.now());
            confirmation.setCode(generateCode());
            return responseSignupDTO;
        }else{
            throw new UserAlreadyExists("User already exists with that email");
        }
    }

    public ResponseSignDTO sign(SignDTO dto) throws EntityNotFoundException,RuntimeException {
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(()->new EntityNotFoundException("User is not signed up"));
        if(!user.isEnabled()){
            throw new EntityNotFoundException("User is not enabled");
        }
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())){
            throw new RuntimeException("Password does not match");
        }
        String token = jwtService.generateToken(user);
        ResponseSignDTO responseSignDTO = new ResponseSignDTO();
        responseSignDTO.setFirstName(user.getFirstName());
        responseSignDTO.setLastName(user.getLastName());
        responseSignDTO.setUserId(user.getId());
        responseSignDTO.setEmail(user.getEmail());
        responseSignDTO.setToken(token);
        return responseSignDTO;
    }

    public UserAuthDTO authenticate(String header){
        String token = header.startsWith("Bearer ") ? header.substring(7) : header;
        System.out.println("Token recibido depues de modificiar: " + token);
        String username = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UnauthorizedException("Unauthorized to perform this operation"));

        return createUserAuthDTO(user);
    }

    public Boolean logout(String token){
        if (token == null || token.trim().isEmpty()) {
            throw new UnauthorizedException("Authorization header missing or invalid");
        }
        try{
            jwtService.invalidateToken();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private Boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }

    private String generateCode(){
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    public Boolean verify(VerifyDTO dto){
        User user = userRepository.findByEmail(dto.getEmail()).orElseThrow(()->new EntityNotFoundException("User is not verified"));
        Confirmation confirmation = confirmationRepository.findByUserId(user.getId()).orElseThrow(()->new EntityNotFoundException("User is not registered"));
        if(confirmation.getCode().equals(dto.getCode())){
            if(confirmation.getConfirmationDate().isAfter(ZonedDateTime.now().minusMinutes(5))){
                user.setActive(true);
                userRepository.save(user);
                Integer response = confirmationRepository.deleteByUserId(user.getId());
                if(response>=1){
                    return true;
                }else {
                    throw new IllegalArgumentException("We could not verify this user");
                }

            }else{
                throw new IllegalArgumentException("The 5 minutes has passed");
            }
        }else{
            throw new IllegalArgumentException("The code does not match");
        }
    }

    public Boolean resend(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("User is not registered"));
        String newCode = generateCode();
        Integer confirmation = confirmationRepository.updateCode(user.getId(), newCode);
        if(confirmation>=1){
            return Boolean.TRUE;
        }else{
            throw new IllegalArgumentException("We could not resend this user");
        }
    }

    public void registerAdress(Long userId, SignupAdressDTO dto){
        User user = userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User is not registered"));
        Adress adress = new Adress();
        adress.setUserId(user.getId());
        adress.setStreet(dto.getStreet());
        adress.setCity(dto.getCity());
        adress.setState(dto.getState());
        adress.setCountry(dto.getCountry());
        adress.setLatitude(dto.getLatitude());
        adress.setLongitude(dto.getLongitude());
        adressRepository.save(adress);
    }




    private UserAuthDTO createUserAuthDTO(User user) {
        UserAuthDTO authDTO = new UserAuthDTO();
        authDTO.setFirstName(user.getFirstName());
        authDTO.setLastName(user.getLastName());
        authDTO.setEmail(user.getEmail());
        authDTO.setFotoUrl(user.getProfilePicture());
        authDTO.setUserId(user.getId());
        return authDTO;
    }









}
