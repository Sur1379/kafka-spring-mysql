package com.example.kafkaspring.service.message.impl;

import com.example.kafkaspring.persistence.model.Message;
import com.example.kafkaspring.persistence.request.MessageRequest;
import com.example.kafkaspring.persistence.response.MessageResponse;
import com.example.kafkaspring.repository.MessageRepository;
import com.example.kafkaspring.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Override
    public MessageResponse save(MessageRequest messageRequest) {
        Message message = messageRepository.save(new Message(messageRequest.getContent()));

        return new MessageResponse(message.getId(), message.getContent());
    }
}
