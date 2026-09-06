package com.example.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * REST controller for the provider service.
 * <p>
 * It returns a unique instance identifier each time the endpoint is called,
 * allowing consumers to distinguish between different provider instances.
 * </p>
 *
 * @author Mahdi-Abedi
 * @since 1.0
 */
@RestController
public class InstanceController {

    /** The port on which this provider instance is running (injected from application.yaml). */
    @Value("${server.port}")
    private String port;

    /** A unique identifier for this instance, generated once at startup. */
    private final String instanceId = UUID.randomUUID().toString();

    /**
     * Returns a string containing the server port and the instance ID.
     *
     * @return instance information
     */
    @GetMapping("/instance-info")
    public String getInstanceInfo() {
        System.out.println("Request received at instance running on port: " + port);
        return "Instance served by Port: " + port + ". Instance ID: " + instanceId;
    }
}