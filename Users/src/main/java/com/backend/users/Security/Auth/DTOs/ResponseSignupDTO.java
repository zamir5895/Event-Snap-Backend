package com.backend.users.Security.Auth.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class ResponseSignupDTO {
    private String firstName;
    private String lastName;
    private String email;
    private Long id;

    public ResponseSignupDTO(){

    }
    public ResponseSignupDTO(String firstName, String lastName, String email, Long id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;

    }


}
