package com.example.kafkaspring;

import com.example.kafkaspring.persistence.request.MessageRequest;
import com.example.kafkaspring.persistence.response.MessageResponse;
import com.example.kafkaspring.service.message.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WriteMessageTest {

    @Autowired
    MessageService messageService;

    @Test
    void testMessages() {
        String content = "test-connection";
        MessageResponse messageResponse = messageService.save(new MessageRequest(content));
        assertNotNull(messageResponse);
        assertNotNull(messageResponse.getId());
        assertEquals(content, messageResponse.getContent());
    }
}