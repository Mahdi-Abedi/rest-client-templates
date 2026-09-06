package com.example.consumer.httpinterface;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.restclient.autoconfigure.RestClientBuilderConfigurer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.client.support.RestTemplateAdapter;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * Configuration class that creates three different implementations of
 * {@link ProviderHttpInterface} using different underlying HTTP clients:
 * <ul>
 *   <li><b>WebClient</b> (reactive, non-blocking) – commented out for now</li>
 *   <li><b>RestClient</b> (modern synchronous) – marked as {@code @Primary}</li>
 *   <li><b>RestTemplate</b> (legacy synchronous)</li>
 * </ul>
 * <p>
 * The RestClient-based implementation uses a load-balanced builder, which resolves
 * the logical service name "provider" via Eureka.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 2.0
 */
@Configuration
public class HttpInterfaceConfig {

    /*@Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }*/

    /**
     * Creates a {@link ProviderHttpInterface} backed by a {@link WebClient}.
     * This is the primary bean (default) due to {@code @Primary}.
     *
     * @return the proxy client that uses WebClient internally
     */
    @Bean
    public ProviderHttpInterface webClientHttpInterface(WebClient.Builder webClientBuilder) {
        // Build a WebClient with base URL
        WebClient webClient = webClientBuilder.baseUrl("http://provider").build();
        // Create an adapter to bridge WebClient with HttpServiceProxyFactory
        WebClientAdapter adapter = WebClientAdapter.create(webClient);
        // Build the factory and create the client proxy
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(ProviderHttpInterface.class);
    }

    /*
    // Plain and @Primary — this is the one the Eureka transport picks up.
    @Bean
    @Primary
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RestClient.Builder restClientBuilder(RestClientBuilderConfigurer configurer) {
        return configurer.configure(RestClient.builder());
    }

    // Load-balanced — gets the interceptor from the bean post processor.
    @Bean
    @LoadBalanced
    public RestClient.Builder loadBalancedRestClientBuilder(RestClientBuilderConfigurer configurer) {
        return configurer.configure(RestClient.builder());
    }*/


    /**
     * Creates a {@link ProviderHttpInterface} backed by a {@link RestClient}.
     * This is the primary bean (default) due to {@code @Primary}.
     * <p>
     * The injected builder is the load-balanced one (qualified with {@code @LoadBalanced}),
     * enabling service discovery via Eureka.
     * </p>
     *
     * @param restClientBuilder the load-balanced RestClient.Builder
     * @return the proxy client using RestClient
     */
    @Bean
    @Primary
    public ProviderHttpInterface restClientHttpInterface(@LoadBalanced RestClient.Builder restClientBuilder) {
        RestClient restClient = restClientBuilder.baseUrl("http://provider").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(ProviderHttpInterface.class);
    }

    /*@Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }*/


    /**
     * Creates a {@link ProviderHttpInterface} backed by a {@link RestTemplate}
     * (the legacy synchronous client, kept for compatibility).
     *
     * @return the proxy client using RestTemplate
     */
    @Bean
    public ProviderHttpInterface restTemplateHttpInterface(RestTemplate restTemplate) {
        // Set a URI template handler to apply a base URL
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory("http://provider"));
        RestTemplateAdapter adapter = RestTemplateAdapter.create(restTemplate);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(ProviderHttpInterface.class);
    }
}