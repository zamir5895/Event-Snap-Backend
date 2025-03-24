package com.backend.users.Email.Service;

import org.springframework.context.ApplicationEvent;

import java.time.ZonedDateTime;

public class CodeVerificationEvent extends ApplicationEvent {
    private final String email;
    private String firstName;
    private String lastName;
    private String code;
    private ZonedDateTime expiryDate;

    public CodeVerificationEvent(String email, String firstName, String lastName, String code, ZonedDateTime expiryDate) {
        super(email);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.code = code;
        this.expiryDate = expiryDate;

    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ZonedDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(ZonedDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}
