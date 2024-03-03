package com.example.kafkaspring;

import com.example.kafkaspring.persistence.model.Message;
import com.example.kafkaspring.persistence.request.MessageRequest;
import com.example.kafkaspring.service.kafka.KafkaMessageConsumer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KafkaConsumerTest {

    private final String TOPIC_NAME = "test-topic";

    private Producer<String, String> producer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private KafkaMessageConsumer kafkaMessageConsumer;

    @Captor
    ArgumentCaptor<MessageRequest> messageArgumentCaptor;

    @BeforeAll
    void setUp() {
        Map<String, Object> configs = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producer = new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), new StringSerializer()).createProducer();
    }

    @AfterAll
    void shutdown() {
        producer.close();
    }

    @Test
    void testKafkaMessages() throws JsonProcessingException {
        String key = "test";
        String requestedMessage = objectMapper.writeValueAsString(new Message("test-message"));
        producer.send(new ProducerRecord<>(TOPIC_NAME, 0, key, requestedMessage));
        producer.flush();

        verify(kafkaMessageConsumer, timeout(5000).times(1))
                .listenKafkaMessages(messageArgumentCaptor.capture());

        MessageRequest message = messageArgumentCaptor.getValue();
        assertNotNull(message);
        assertEquals("test-message", message.getContent());
    }
}