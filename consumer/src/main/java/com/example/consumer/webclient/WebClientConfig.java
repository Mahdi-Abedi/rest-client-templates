package com.example.consumer.webclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for creating a {@link WebClient} bean.
 * <p>
 * Unlike RestClient/RestTemplate, WebClient is designed for reactive, non-blocking
 * communication. However, it can also be used in a blocking way by calling
 * {@code .block()} on the response (as shown in {@link ProviderWebClient}).
 * </p>
 * <p>
 * <b>Note:</b> Because this project also includes {@code spring-boot-starter-web}
 * (servlet-based), the auto-configuration for WebClient is NOT activated.
 * Therefore, we manually create the builder and the WebClient bean.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@Configuration
public class WebClientConfig {

    /**
     * Creates a {@link WebClient.Builder} bean with a pre-configured base URL.
     *
     * @return the builder instance
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder().baseUrl("http://localhost:8081");
    }

    /**
     * Creates a {@link WebClient} bean using the builder.
     *
     * @param builder the WebClient.Builder (injected from the method above)
     * @return a configured WebClient
     */
    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}