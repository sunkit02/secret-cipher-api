package com.sunkit.secretcipher;

import com.sunkit.secretcipher.models.user.User;
import com.sunkit.secretcipher.repos.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SecretCipherApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecretCipherApiApplication.class, args);
    }

    @Bean
    CommandLineRunner createUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        return args -> {
            User user = User.builder()
                    .username("sunkit25")
                    .password(passwordEncoder.encode("password"))
                    .email("sunkit@scipher.com")
                    .build();
            if (!userRepository.existsByUsername(user.getUsername()))
                userRepository.save(user);
        };
    }
}
