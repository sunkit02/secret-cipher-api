package com.sunkit.secretcipher.models.message;

import com.sunkit.secretcipher.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.Length;

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
    @Length(max = 255, message = "The key must not exceed 255 characters")
    @Builder.Default
    private final String encodingKey = "";
    @Length(max = 255, message = "The subject must not exceed 255 characters")
    @Builder.Default
    private final String subject = "Subject: blank";

    @Length(max = 1000, message = "Message content must not exceed 1,000 characters")
    @NotBlank(message = "Message content cannot be blank")
    @Builder.Default
    @Column(length = 1000, nullable = false)
    private final String message = "";

    @Column(length = 1200, nullable = false)
    private String encodedMessage;

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
