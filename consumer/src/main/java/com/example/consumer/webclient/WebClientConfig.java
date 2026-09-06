package com.example.consumer.webclient;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for creating a load-balanced {@link WebClient} bean.
 * <p>
 * WebClient is reactive and non-blocking. Even though this project uses a
 * servlet-based web stack (spring-boot-starter-web), WebClient can still be used
 * in a blocking way (via {@code .block()}) for synchronous calls.
 * </p>
 * <p>
 * The {@code @LoadBalanced} annotation ensures that the builder uses
 * {@code LoadBalancerExchangeFilterFunction} to resolve logical service names.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 2.0
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates a load-balanced {@link WebClient.Builder}.
     *
     * @return a load-balanced WebClient.Builder
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    /**
     * Creates a {@link WebClient} bean with a base URL set to "http://provider".
     * The injected builder is the load-balanced one.
     *
     * @param builder the load-balanced WebClient.Builder
     * @return a configured WebClient
     */
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("http://provider")
                .build();
    }
}