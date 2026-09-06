package com.example.consumer.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service class that uses {@link RestTemplate} to call the provider service.
 * <p>
 * This demonstrates the legacy synchronous way of making HTTP calls.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class RestTemplateClient {

    /** Base URL of the provider service. */
    public static final String PROVIDER_URL = "http://localhost:8081";

    /** The RestTemplate bean (injected from {@link RestTemplateConfig}). */
    public final RestTemplate restTemplate;

    /**
     * Fetches instance information from the provider.
     *
     * @return instance details as a string
     */
    public String getInstanceInfo() {
        return restTemplate.getForObject(PROVIDER_URL + "/instance-info", String.class);
    }
}