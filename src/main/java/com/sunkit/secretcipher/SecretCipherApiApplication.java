package com.sunkit.secretcipher;

import com.sunkit.secretcipher.models.user.User;
import com.sunkit.secretcipher.repos.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecretCipherApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecretCipherApiApplication.class, args);
    }

    @Bean
    CommandLineRunner createUser(
            UserRepository userRepository) {
        return args -> {
            User user = User.builder()
                    .username("sunkit25")
                    .password("password")
                    .email("sunkit@scipher.com")
                    .build();
            if (!userRepository.existsByUsername(user.getUsername()))
                userRepository.save(user);
        };
    }
}
