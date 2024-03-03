package com.example.kafkaspring.repository;

import com.example.kafkaspring.persistence.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
