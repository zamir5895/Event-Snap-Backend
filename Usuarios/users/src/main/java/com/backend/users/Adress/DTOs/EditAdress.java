package com.backend.users.Adress.DTOs;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class EditAdress {

    private String street;
    private String city;
    private String state;
    private String country;
    private Double latitude;
    private Double longitude;

}
