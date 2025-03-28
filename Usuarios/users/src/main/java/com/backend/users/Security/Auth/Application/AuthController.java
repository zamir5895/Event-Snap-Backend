package com.backend.users.Security.Auth.Application;

import com.backend.users.Security.Auth.DTOs.*;
import com.backend.users.Security.Auth.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("signup")
    public ResponseEntity<?> signup(@RequestBody    SignupDTO dto){
        try{
            ResponseSignupDTO responseSignupDTO = authService.signup(dto);
            return ResponseEntity.ok(responseSignupDTO);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("sign")
    public ResponseEntity<?> sign(@RequestBody SignDTO dto){
        try{
            ResponseSignDTO response = authService.sign(dto);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("verify")
    public ResponseEntity<?> verify(@RequestBody VerifyDTO dto){
        try {
            boolean result = authService.verify(dto);
            return ResponseEntity.ok(result);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("resend/{email}")
    public ResponseEntity<?> resend(@PathVariable String email){
        try{
            Boolean res = authService.resend(email);
            return ResponseEntity.ok(res);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("adress/{id}")
    public ResponseEntity<?> adress(@PathVariable Long id, @RequestBody SignupAdressDTO dto){
        try{
            authService.registerAdress(id, dto);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PostMapping("logout")
    public ResponseEntity logout(@RequestHeader String token){
        try{
            Boolean res = authService.logout(token);
            return ResponseEntity.ok(res);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
