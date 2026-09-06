package com.example.consumer.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Service class that uses {@link WebClient} to call the provider service.
 * <p>
 * Note: Although WebClient is reactive, this implementation uses {@code .block()}
 * to make it behave synchronously. In a truly reactive application, you would
 * return {@code Mono<String>} instead.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProviderWebClient {

    /** The WebClient instance (injected from {@link WebClientConfig}). */
    private final WebClient webClient;

    /**
     * Fetches instance information from the provider.
     * This method blocks the calling thread until the response arrives.
     *
     * @return instance details as a string
     */
    public String getInstanceInfo() {
        return webClient.get()
                .uri("/instance-info")
                .retrieve()
                .bodyToMono(String.class)
                .block();  // Blocks to make it synchronous
    }
}