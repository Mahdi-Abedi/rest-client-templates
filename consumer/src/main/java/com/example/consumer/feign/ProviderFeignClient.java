package com.example.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Feign client interface for consuming the provider service.
 * Feign simplifies HTTP client development with declarative annotations.
 * <p>
 * The {@code name} attribute is used for service discovery (when using Eureka),
 * and {@code url} specifies the fixed endpoint for standalone usage.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@FeignClient(name = "provider-service", url = "http://localhost:8081")
public interface ProviderFeignClient {

    /**
     * Calls the provider's {@code /instance-info} endpoint.
     *
     * @return a string containing instance details (port and instance ID)
     */
    @GetMapping("/instance-info")
    String getInstanceInfo();
}