package com.example.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Feign client interface for consuming the provider service.
 * Feign simplifies HTTP client development with declarative annotations.
 * <p>
 * The {@code name} attribute is used for service discovery (when using Eureka).
 * Since no {@code url} is provided, Feign will resolve the service name "provider"
 * via the configured discovery client (Eureka) and apply load balancing.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 2.0
 */
@FeignClient(name = "provider")
public interface ProviderFeignClient {

    /**
     * Calls the provider's {@code /instance-info} endpoint.
     *
     * @return a string containing instance details (port and instance ID)
     */
    @GetMapping("/instance-info")
    String getInstanceInfo();
}