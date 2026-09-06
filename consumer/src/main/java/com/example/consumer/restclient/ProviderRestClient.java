package com.example.consumer.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * Service class that encapsulates the logic for calling the provider service
 * using {@link RestClient}.
 * <p>
 * This service uses the injected {@code RestClient} bean (defined in
 * {@link RestClientConfig}) to make synchronous HTTP requests.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class ProviderRestClient {

    /** The RestClient instance used to communicate with the provider. */
    private final RestClient restClient;

    /**
     * Fetches instance information from the provider's {@code /instance-info} endpoint.
     *
     * @return a string containing the instance details
     */
    public String getInstanceInfo() {
        return restClient.get()
                .uri("/instance-info")
                .retrieve()
                .body(String.class);
    }
}