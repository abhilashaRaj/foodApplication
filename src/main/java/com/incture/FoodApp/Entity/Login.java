package com.incture.FoodApp.Entity;

public class Login {
    private String email;
    private String userId;

    public Login(String email, String userId) {
        this.email = email;
        this.userId = userId;
    }

    public Login() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
