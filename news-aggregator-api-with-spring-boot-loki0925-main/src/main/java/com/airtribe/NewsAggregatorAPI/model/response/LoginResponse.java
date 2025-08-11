package com.airtribe.NewsAggregatorAPI.model.response;

public class LoginResponse {
    private String token;
    private String tokenType = "Bearer";
    private String email;

    public LoginResponse(String token, String tokenType, String email) {
        this.token = token;
        this.tokenType = tokenType;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
