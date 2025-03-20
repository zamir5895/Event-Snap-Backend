package com.backend.users.Security.Auth.Application;


import com.backend.users.Security.Auth.DTOs.ResponseSignupDTO;
import com.backend.users.Security.Auth.DTOs.SignupDTO;
import com.backend.users.Security.Auth.Domain.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("signupp")
    public ResponseEntity<?> signup(@RequestBody SignupDTO dto){
        try{
            ResponseSignupDTO responseSignupDTO = authService.signup(dto);
            return ResponseEntity.ok(responseSignupDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
