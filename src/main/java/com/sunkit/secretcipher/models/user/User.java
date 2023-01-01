package com.sunkit.secretcipher.models.user;

import com.sunkit.secretcipher.models.message.Message;
import com.sunkit.secretcipher.security.auth.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.Length;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_gen")
    @SequenceGenerator(name = "users_gen", sequenceName = "users_seq", allocationSize = 1)
    @Column(name = "user_id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;

    @Length(min = 6, max = 20, message = "Username must be between 6 and 20 characters.")
    @NotNull(message = "Username cannot be empty.")
    @Column(nullable = false, unique = true)
    private String username;

    @NotNull(message = "Password cannot be empty.")
    @Column(length = 60, nullable = false)
    private String password;

    @Email(message = "Must be a valid email address")
    @Column(unique = true)
    private String email;

    @OneToMany(
            mappedBy = "sender",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @Builder.Default
    private Set<Message> messagesSent = new HashSet<>();

    @OneToMany(
            mappedBy = "recipient",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @Builder.Default
    private Set<Message> messagesReceived = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.USER;
}
