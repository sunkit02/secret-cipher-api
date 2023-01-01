package com.sunkit.secretcipher.services;

import com.sunkit.secretcipher.expections.UserNotFoundException;
import com.sunkit.secretcipher.expections.UsernameAndPasswordAuthenticationException;
import com.sunkit.secretcipher.models.payloads.responses.JwtTokens;
import com.sunkit.secretcipher.models.payloads.responses.LoginResponse;
import com.sunkit.secretcipher.models.user.User;
import com.sunkit.secretcipher.security.jwt.JwtConfig;
import com.sunkit.secretcipher.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public LoginResponse loginWithUsernameAndPassword(String username, String password) throws UserNotFoundException, UsernameAndPasswordAuthenticationException {
        User user = userService.findByUsername(username);
        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
        if (!passwordMatches) {
            throw new UsernameAndPasswordAuthenticationException(
                    "Password hashes don't match");
        }

        JwtTokens jwtTokens = generateJwtTokens(user.getUsername(), user.getRole().getGrantedAuthorities());
        return LoginResponse.of(user, jwtTokens);
    }

    private JwtTokens generateJwtTokens(String subject,
                                        Collection<? extends GrantedAuthority> authorities) {
        String accessToken = JwtUtils.generateToken(
                subject,
                authorities,
                jwtConfig.getAccessTokenExpirationAfterDays(),
                secretKey);

        String refreshToken = JwtUtils.generateToken(
                subject,
                authorities,
                jwtConfig.getRefreshTokenExpirationAfterDays(),
                secretKey);

        return JwtTokens.of(accessToken, refreshToken);

    }
}
