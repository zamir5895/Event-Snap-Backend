package com.backend.users.Users.Application;


import com.backend.users.Adress.DTOs.AdressPublish;
import com.backend.users.Adress.DTOs.EditAdress;
import com.backend.users.Users.Domain.UserService;
import com.backend.users.Users.UserDTO.ProfileDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }
    /*
     Enpoints
     Deltede user
     get User profile
     edit ubication
     Edit information
     Edit profile picture
     Change password
     add adress
     delete adress
     */


    @GetMapping("/profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId) {
        try {
            ProfileDTO profile = userService.getUserById(userId);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/adress/{userId}")
    public ResponseEntity<?> addAdress(@PathVariable Long userId,@Validated @RequestBody AdressPublish adress) {
        try {
            userService.addAdress(userId, adress);
            return ResponseEntity.ok("Adress added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/adress/{userId}")
    public ResponseEntity<?> editAdress(@PathVariable Long userId, @RequestBody EditAdress adress) {
        try {
            userService.editAdress(userId, adress);
            return ResponseEntity.ok("Adress updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/adress/{userId}")
    public ResponseEntity<?> deleteAdress(@PathVariable Long userId){
        try{
            userService.deleteAdress(userId);
            return ResponseEntity.accepted("Adress deleted successfully").build();
        }catch (Exception e){
            return ResponseEntity.badRequest
        }
    }






}
