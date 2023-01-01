package com.sunkit.secretcipher.security;

import com.sunkit.secretcipher.security.jwt.JwtConfig;
import com.sunkit.secretcipher.security.jwt.JwtTokenVerificationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;


    @Bean
    public SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAfter(new JwtTokenVerificationFilter(secretKey, jwtConfig), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                // allow websocket connections
                // todo: secure websockets
                .requestMatchers("/updates/**").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }
}
