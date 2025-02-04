package com.producer;

import com.producer.service.ConsumerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProducerTests {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private ConsumerService consumerService;

    private int successCount;
    private int failureCount;

    @BeforeEach
    public void setUp() {
        successCount = 0;
        failureCount = 0;
    }

    @Test
    public void testMessageProcessedSuccessfully() {
        kafkaTemplate.send("test-topic", "Hello, world!");

        consumerService.listen("Hello, world!");

        assertEquals(1, consumerService.getSuccessCount(), "Success count should be 1");
        assertEquals(0, consumerService.getFailureCount(), "Failure count should be 0");
    }

    @Test
    public void testMessageProcessingFailure() {
        kafkaTemplate.send("test-topic", "fail");

        consumerService.listen("fail");

        assertEquals(0, consumerService.getSuccessCount(), "Success count should be 0");
        assertEquals(1, consumerService.getFailureCount(), "Failure count should be 1");
    }
}
