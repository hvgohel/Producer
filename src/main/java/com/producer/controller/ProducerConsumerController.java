package com.producer.controller;

import com.producer.service.ConsumerService;
import com.producer.service.ProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProducerConsumerController {
    private final ProducerService producerService;
    private final ConsumerService consumerService;

    @GetMapping("/send")
    public void send(@RequestParam String message) {
        producerService.sendMessage(message);
    }

    @GetMapping("/metrics")
    public String getKafkaMetrics() {
        int successCount = consumerService.getSuccessCount();
        int failureCount = consumerService.getFailureCount();
        return "Success Count: " + successCount + ", Failure Count: " + failureCount;
    }
}