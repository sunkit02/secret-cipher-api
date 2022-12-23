package com.sunkit.secretcipher.models.user;

import com.sunkit.secretcipher.models.message.Message;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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

    private String username;
    private String password;
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
}
