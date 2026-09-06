package com.example.consumer.resttemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;

/**
 * REST controller that exposes an endpoint to test the RestTemplate-based service.
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@RestController
@RequestMapping("/api/rest-template")
@RequiredArgsConstructor
public class RestTemplateController {

    /** The service that uses RestTemplate. */
    public final RestTemplateClient restTemplateClient;

    /**
     * Calls the provider's instance info endpoint via RestTemplate.
     *
     * @return instance information
     */
    @GetMapping("/instance")
    public String getInstance() {
        //return new RestTemplate().getForObject("http://localhost:8081/instance-info", String.class);

        return restTemplateClient.getInstanceInfo();
    }
}
