package com.example.consumer.resttemplate;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for creating a load-balanced {@link RestTemplate} bean.
 * <p>
 * The {@code @LoadBalanced} annotation adds the {@code LoadBalancerInterceptor}
 * to the RestTemplate, allowing it to resolve logical service names (e.g., "provider")
 * via Eureka or other service discovery mechanisms.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 2.0
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Creates a load-balanced {@link RestTemplate} bean.
     *
     * @param builder the auto-configured RestTemplateBuilder
     * @return a load-balanced RestTemplate
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}