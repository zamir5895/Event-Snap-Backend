package com.backend.users.Security.Utils;

import java.time.ZonedDateTime;

public class ErrorDetail {
    private  String error;
    private String message;
    private Integer code;
    private ZonedDateTime timestamp;

    public ErrorDetail(String error, Integer code, ZonedDateTime timestamp, String message) {
        this.error = error;
        this.code = code;
        this.timestamp = timestamp;
        this.message = message;
    }
    public ErrorDetail(){}

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
