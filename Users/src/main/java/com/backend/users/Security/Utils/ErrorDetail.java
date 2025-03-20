package com.backend.users.Security.Utils;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ErrorDetail {
    private  String error;
    private String message;
    private Integer code;
    private ZonedDateTime timestamp;
}
