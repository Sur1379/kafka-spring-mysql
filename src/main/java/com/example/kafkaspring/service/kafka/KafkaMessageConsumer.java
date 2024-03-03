package com.example.kafkaspring.service.kafka;


import com.example.kafkaspring.persistence.request.MessageRequest;
import com.example.kafkaspring.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageConsumer {

    @Autowired
    MessageService messageService;

    @KafkaListener(topics = "test-topic",
            concurrency = "${spring.kafka.consumer.level.concurrency:3}")
    public void listenKafkaMessages(@Payload MessageRequest messageRequest) {
        messageService.save(messageRequest);
    }
}
