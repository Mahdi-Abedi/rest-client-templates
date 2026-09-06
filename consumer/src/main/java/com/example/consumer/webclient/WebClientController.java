package com.example.consumer.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.reactive.function.client.WebClient;

/**
 * REST controller that exposes an endpoint to test the WebClient-based service.
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@RestController
@RequestMapping("/api/web-client")
@RequiredArgsConstructor
public class WebClientController {

    /** The service that uses WebClient. */
    private final ProviderWebClient providerWebClient;

    /**
     * Calls the provider's instance info endpoint via WebClient.
     *
     * @return instance information
     */
    @GetMapping("/instance")
    public String getInstance() {
        /*return WebClient.create().get()
                .uri("http://localhost:8081")
                .retrieve()
                .bodyToMono(String.class)
                .block();*/

        return providerWebClient.getInstanceInfo();
    }
}
