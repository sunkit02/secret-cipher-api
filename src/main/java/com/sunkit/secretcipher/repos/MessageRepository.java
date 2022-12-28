package com.sunkit.secretcipher.repos;

import com.sunkit.secretcipher.models.message.Message;
import com.sunkit.secretcipher.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllBySenderOrderByTimeSentDesc(User user);
}
