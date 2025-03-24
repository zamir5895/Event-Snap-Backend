package com.backend.users.Security.Auth.DTOs;


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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
