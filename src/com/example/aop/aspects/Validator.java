package com.example.aop.aspects;

public class Validator {
    public void validateEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        System.out.println("Valid email: " + email);
    }
}
