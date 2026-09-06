package com.example.consumer.resttemplate;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for creating a {@link RestTemplate} bean.
 * <p>
 * <b>Important:</b> In Spring Boot 4.0+, {@link RestTemplateBuilder} has moved
 * to the {@code spring-boot-starter-restclient} module. This configuration
 * works because we have added that dependency.
 * </p>
 * <p>
 * While {@link RestTemplate} is still supported, it is considered legacy.
 * For new projects, prefer {@link RestClient} or {@link WebClient}.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Creates a {@link RestTemplate} bean using the provided builder.
     * The builder allows for fine-tuning (timeouts, interceptors, etc.).
     *
     * @param builder the auto-configured RestTemplateBuilder (from spring-boot-starter-restclient)
     * @return a configured RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}