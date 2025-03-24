package com.backend.users.Security.Auth.DTOs;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupAdressDTO {
    private String street;
    private String city;
    private String state;
    private String country;
    private Double latitude;
    private Double longitude;
}
