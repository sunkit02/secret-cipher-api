package com.sunkit.secretcipher.models.payloads.responses;

public record JwtTokens(String accessToken, String refreshToken) {
    public static JwtTokens of(String accessToken, String refreshToken) {
        return new JwtTokens(accessToken, refreshToken);
    }
}
