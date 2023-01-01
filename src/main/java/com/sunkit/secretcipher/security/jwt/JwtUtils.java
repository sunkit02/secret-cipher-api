package com.sunkit.secretcipher.security.jwt;

import io.jsonwebtoken.Jwts;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

public class JwtUtils {

    private static final String AUTHORITIES_HEADER = "authorities";

    public static String generateToken(@NotNull String subject,
                                       @NotNull Collection<? extends GrantedAuthority> authorities,
                                       @NotNull Integer daysUntilExpiration,
                                       @NotNull SecretKey secretKey) {
        return Jwts.builder()
                .setSubject(subject)
                .claim(AUTHORITIES_HEADER, authorities)
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(
                        LocalDate.now().plusDays(daysUntilExpiration)))
                .signWith(secretKey)
                .compact();
    }
}
