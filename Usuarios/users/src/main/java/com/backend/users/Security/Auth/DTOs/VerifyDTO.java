package com.backend.users.Security.Auth.DTOs;

import lombok.Data;

@Data
public class VerifyDTO {
    String code;
    String email;
}
