package com.airtribe.NewsAggregatorAPI.model.response;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
    private int status;
    private List<String> errors;
    private String message;

    public ErrorResponse(int status, List<String> errors, String message) {
        this.status = status;
        this.errors = errors;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
