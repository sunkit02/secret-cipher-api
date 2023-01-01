package com.sunkit.secretcipher.security.jwt;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtConfig {
    private final String secretKey = "secret_key".repeat(10);
    private final String tokenPrefix = "Bearer ";
    private final Integer accessTokenExpirationAfterDays = 1;
    private final Integer refreshTokenExpirationAfterDays = 14;
    private final String authoritiesHeader = "authorities";
}
