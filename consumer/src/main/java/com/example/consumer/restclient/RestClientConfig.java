package com.example.consumer.restclient;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.restclient.autoconfigure.RestClientBuilderConfigurer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClient;

/**
 * Configuration for RestClient with proper Eureka integration.
 * <p>
 * This configuration resolves the critical issue where Eureka's internal HTTP client
 * incorrectly picks up a load-balanced RestClient.Builder, causing:
 * "No instances available for localhost".
 * </p>
 * <p>
 * Solution: Provide two separate builders:
 * <ul>
 *   <li><b>@Primary builder</b> – plain, for Eureka's internal transport</li>
 *   <li><b>@LoadBalanced builder</b> – for application service calls</li>
 * </ul>
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 2.0
 * @see <a href="https://github.com/spring-cloud/spring-cloud-netflix/issues/4524">GitHub Issue #4524</a>
 */
@Configuration
public class RestClientConfig {

    /**
     * Plain builder for Eureka's internal HTTP client.
     * Marked as @Primary so Eureka picks this one, not the load-balanced version.
     *
     * @param configurer applies Spring Boot defaults to the builder
     * @return a plain RestClient.Builder
     */
    @Bean
    @Primary
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RestClient.Builder restClientBuilder(RestClientBuilderConfigurer configurer) {
        return configurer.configure(RestClient.builder());
    }

    /**
     * Load-balanced builder for application code.
     * Use this to call services via logical names (e.g., "http://provider").
     *
     * @param configurer applies Spring Boot defaults to the builder
     * @return a load-balanced RestClient.Builder
     */
    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder(RestClientBuilderConfigurer configurer) {
        return configurer.configure(RestClient.builder());
    }

    /**
     * Creates a RestClient using the load-balanced builder.
     *
     * @param builder the load-balanced builder (injected via @LoadBalanced qualifier)
     * @return a RestClient configured to call the "provider" service via Eureka
     */
    @Bean
    public RestClient restClient(@LoadBalanced RestClient.Builder builder) {
        return builder
                .baseUrl("http://provider")
                .build();
    }
}