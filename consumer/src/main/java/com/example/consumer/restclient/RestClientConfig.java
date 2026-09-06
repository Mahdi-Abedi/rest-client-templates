package com.example.consumer.restclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for creating a {@link RestClient} bean.
 * <p>
 * {@link RestClient} is the modern replacement for {@link RestTemplate},
 * introduced in Spring Boot 3.2. It provides a fluent API and better integration
 * with Spring's HTTP interface support.
 * </p>
 * <p>
 * Note: Since we have added {@code spring-boot-starter-restclient}, the
 * {@link RestClient.Builder} is automatically available as a bean
 * (via {@code RestClientAutoConfiguration}). We inject it here to customize
 * the base URL.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@Configuration
public class RestClientConfig {

    /**
     * Creates a {@link RestClient} bean with a pre-configured base URL.
     * The {@link RestClient.Builder} is provided by Spring Boot's auto-configuration.
     *
     * @param builder the auto-configured builder (injected by Spring)
     * @return a customized RestClient instance
     */
    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:8081")
                .build();
    }
}