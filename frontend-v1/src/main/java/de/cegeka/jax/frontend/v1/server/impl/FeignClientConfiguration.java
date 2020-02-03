package de.cegeka.jax.frontend.v1.server.impl;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

import de.cegeka.jax.backend.v1.api.DefaultApiClient;

@Configuration
@EnableFeignClients(basePackageClasses = DefaultApiClient.class)
public class FeignClientConfiguration {
}
