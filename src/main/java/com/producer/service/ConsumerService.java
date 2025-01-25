package com.producer.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {
    private int successCount = 0;
    private int failureCount = 0;

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void listen(String message) {
        try {
            processMessage(message);
            successCount++;
            System.out.println("Processed: " + message);
        } catch (Exception e) {
            failureCount++;
            System.err.println("Failed to process message: " + message + " due to error: " + e.getMessage());
        }
    }

    private void processMessage(String message) throws Exception {
        if (message.contains("fail")) {
            throw new Exception("Simulated failure");
        }
    }

    public int getSuccessCount() {
        return successCount;
    }

    public int getFailureCount() {
        return failureCount;
    }
}