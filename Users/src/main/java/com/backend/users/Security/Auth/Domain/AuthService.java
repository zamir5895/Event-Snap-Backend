package com.backend.users.Security.Auth.Domain;


import com.backend.users.Confirmation.Confirmation;
import com.backend.users.Security.Auth.DTOs.ResponseSignupDTO;
import com.backend.users.Security.Auth.DTOs.SignupDTO;
import com.backend.users.Security.Exceptions.UserAlreadyExists;
import com.backend.users.Security.JWT.JwtService;
import com.backend.users.User.Domain.User;
import com.backend.users.User.Domain.UserService;
import com.backend.users.User.Infrastructure.UserRepository;
import org.apache.coyote.BadRequestException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
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

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService
                        , ApplicationEventPublisher eventPublisher, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.eventPublisher = eventPublisher;
        this.userService = userService;
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

    private Boolean validateEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,6}$";
        return email.matches(emailRegex);
    }

    private String generateCode(){
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }







}
