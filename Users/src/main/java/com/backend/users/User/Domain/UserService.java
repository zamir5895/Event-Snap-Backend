package com.backend.users.User.Domain;

import com.backend.users.User.Infrastructure.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsService userDetailsService(){
        return  username -> userRepository.
                findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }


}
