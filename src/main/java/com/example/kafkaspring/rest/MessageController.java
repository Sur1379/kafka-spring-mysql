package com.example.kafkaspring.rest;

import com.example.kafkaspring.persistence.request.MessageRequest;
import com.example.kafkaspring.persistence.response.ApiResponse;
import com.example.kafkaspring.persistence.response.MessageResponse;
import com.example.kafkaspring.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody MessageRequest messageRequest) {
        MessageResponse messageResponse = messageService.save(messageRequest);
        return ResponseEntity.ok(new ApiResponse("Message with ID - " + messageResponse.getId() + " created successfully"));
    }
}
