package com.sunkit.secretcipher.models.payloads.requests;

public record RegisterNewUserRequest(String username, String password, String email) {
}
