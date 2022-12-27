package com.sunkit.secretcipher.models.message;

import com.sunkit.secretcipher.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messages_gen")
    @SequenceGenerator(name = "messages_gen", sequenceName = "messages_seq", allocationSize = 1)
    @Column(name = "message_id", nullable = false)
    @JdbcTypeCode(SqlTypes.BIGINT)
    private Long id;
    @Builder.Default
    private final String encodingKey = "";
    @NotBlank(message = "Message content cannot be blank")
    @Builder.Default
    private final String message = "";

    @NotNull
    @Builder.Default
    private final Timestamp timeSent = new Timestamp(System.currentTimeMillis());

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "sender_id",
            referencedColumnName = "user_id",
            nullable = false
    )
    private User sender;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "recipient_id",
            referencedColumnName = "user_id",
            nullable = false
    )
    private User recipient;
}
