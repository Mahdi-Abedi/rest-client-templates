package com.example.consumer.httpinterface;

import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * Declarative HTTP client interface using Spring's {@code @HttpExchange} annotation.
 * <p>
 * This approach is part of Spring Framework 6+ and provides a unified way to define
 * HTTP clients without being tied to a specific implementation (RestClient, WebClient, etc.).
 * </p>
 * <p>
 * The actual client is created using {@link org.springframework.web.service.invoker.HttpServiceProxyFactory}
 * with an appropriate adapter (RestClientAdapter, WebClientAdapter, or RestTemplateAdapter).
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@HttpExchange  // Base exchange configuration (can include URL, headers, etc.)
public interface ProviderHttpInterface {

    /**
     * Sends a GET request to {@code /instance-info} on the provider service.
     *
     * @return the instance information as a plain string
     */
    @GetExchange("/instance-info")
    String getInstance();
}