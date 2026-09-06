package com.example.consumer.restclient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestClient;

/**
 * REST controller that exposes an endpoint to test the RestClient-based service.
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@RestController
@RequestMapping("/api/rest-client")
@RequiredArgsConstructor
public class RestClientController {

    /** The service that uses RestClient to call the provider. */
    private final ProviderRestClient providerRestClient;

    /**
     * Calls the provider's instance info endpoint via RestClient.
     *
     * @return instance information
     */
    @GetMapping("/instance")
    public String getInstance() {
        /*return RestClient.create().get()
                .uri("http://localhost:8081")
                .retrieve()
                .body(String.class);*/

        return providerRestClient.getInstanceInfo();
    }
}
