package com.example.kafkaspring.service.message;

import com.example.kafkaspring.persistence.request.MessageRequest;
import com.example.kafkaspring.persistence.response.MessageResponse;

public interface MessageService {

    MessageResponse save(MessageRequest messageRequest);
}
