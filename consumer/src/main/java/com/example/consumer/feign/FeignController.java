package com.example.consumer.feign;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes an endpoint to test the Feign client.
 * <p>
 * The Feign client is injected via constructor injection (using Lombok's {@code @RequiredArgsConstructor}).
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@RestController
@RequestMapping("/api/feign")
@RequiredArgsConstructor
public class FeignController {

    /** The Feign client used to communicate with the provider service. */
    private final ProviderFeignClient providerFeignClient;

    /**
     * Retrieves instance information from the provider service via Feign.
     *
     * @return the instance info as a string
     */
    @GetMapping("/instance")
    public String getInstance() {
        return providerFeignClient.getInstanceInfo();
    }
}