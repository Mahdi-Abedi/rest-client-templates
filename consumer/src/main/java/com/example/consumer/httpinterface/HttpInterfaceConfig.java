package com.example.consumer.httpinterface;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * Configuration class that creates three different implementations of the same
 * {@link ProviderHttpInterface} using different underlying HTTP clients:
 * <ul>
 *   <li><b>WebClient</b> (reactive, non-blocking)</li>
 *   <li><b>RestClient</b> (modern synchronous)</li>
 *   <li><b>RestTemplate</b> (legacy synchronous)</li>
 * </ul>
 * <p>
 * The {@code @Primary} annotation marks the WebClient-based implementation
 * as the default bean when no qualifier is specified.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@Configuration
public class HttpInterfaceConfig {

    /**
     * Creates a {@link ProviderHttpInterface} backed by a {@link WebClient}.
     * This is the primary bean (default) due to {@code @Primary}.
     *
     * @return the proxy client that uses WebClient internally
     */
    @Bean
    @Primary
    public ProviderHttpInterface webClientHttpInterface() {
        // Build a WebClient with base URL
        WebClient webClient = WebClient.create("http://localhost:8081");
        // Create an adapter to bridge WebClient with HttpServiceProxyFactory
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        // Build the factory and create the client proxy
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(ProviderHttpInterface.class);
    }

    /**
     * Creates a {@link ProviderHttpInterface} backed by a {@link RestClient}
     * (the modern synchronous HTTP client).
     *
     * @return the proxy client using RestClient
     */
    @Bean
    public ProviderHttpInterface restClientHttpInterface() {
        RestClient restClient = RestClient.create("http://localhost:8081");
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(ProviderHttpInterface.class);
    }

    /**
     * Creates a {@link ProviderHttpInterface} backed by a {@link RestTemplate}
     * (the legacy synchronous client, kept for compatibility).
     *
     * @return the proxy client using RestTemplate
     */
    @Bean
    public ProviderHttpInterface restTemplateHttpInterface() {
        RestTemplate restTemplate = new RestTemplate();
        // Set a URI template handler to apply a base URL
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://localhost:8081"));
        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplate);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(ProviderHttpInterface.class);
    }
}