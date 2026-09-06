package com.example.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Provider microservice that returns instance information.
 * <p>
 * This service runs on port 8081 and exposes a single endpoint {@code /instance-info}.
 * It returns a string containing the server port and a unique instance ID.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}