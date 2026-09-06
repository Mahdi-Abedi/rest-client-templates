package com.example.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Main entry point for the Consumer microservice.
 * This application demonstrates four different ways to consume REST APIs:
 * <ul>
 *   <li>OpenFeign (declarative REST client)</li>
 *   <li>WebClient (reactive, non-blocking)</li>
 *   <li>RestClient (modern synchronous HTTP client introduced in Spring Boot 3.2)</li>
 *   <li>RestTemplate (legacy synchronous client, maintained for compatibility)</li>
 *   <li>Http Interface (using @HttpExchange to define client interfaces)</li>
 * </ul>
 *
 * @author Mahdi-Abedi
 * @version 1.0
 * @since 2026-09-06
 */
@SpringBootApplication
@EnableFeignClients  // Enables OpenFeign client scanning
public class ConsumerApplication {

    /**
     * Launches the Spring Boot application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}