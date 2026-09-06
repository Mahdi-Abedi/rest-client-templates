package com.example.consumer.httpinterface;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes an endpoint to test the Http Interface client.
 * <p>
 * By default, it uses the {@code @Primary} bean (WebClient-based) defined in
 * {@link HttpInterfaceConfig}. To use a different implementation,
 * you would need to add {@code @Qualifier} on the field.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@RestController
@RequestMapping("/api/http-interface")
@RequiredArgsConstructor
public class HttpInterfaceController {

    /** The HTTP interface client (default is WebClient-based). */
    private final ProviderHttpInterface providerHttpInterface;

    /**
     * Calls the provider's instance info endpoint via the HTTP interface.
     *
     * @return instance information
     */
    @GetMapping("/instance")
    public String getInstance() {
        return providerHttpInterface.getInstance();
    }
}