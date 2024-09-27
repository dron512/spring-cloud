package com.mh.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;

//@Configuration
public class FilterConfig {

    private final Environment environment;

    public FilterConfig(Environment environment) {
        this.environment = environment;
    }

//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        System.out.println(environment.getProperty("first-service-url"));
        System.out.println("environment.getActiveProfiles()");
        System.out.println(environment.getProperty("spring.cloud.gateway.routes[0].uri"));
        return builder.routes()
                .route(r -> r.path("/first/**")
                    .filters(f -> f.addRequestHeader("first-request", "first-request-header")
                            .addResponseHeader("first-response", "first-response-header"))
                    .uri("lb://FIRST-SERVICE"))
                .route(r -> r.path("/second/**")
                        .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                .addResponseHeader("second-response", "seoncd-response-header"))
                        .uri("lb://SECOND-SERVICE"))
                .build();
    }
}
