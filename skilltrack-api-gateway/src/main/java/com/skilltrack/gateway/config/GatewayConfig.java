package com.skilltrack.gateway.config;

import com.skilltrack.gateway.filters.JwtGatewayFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtGatewayFilter filter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("skilltrack-auth-service", r -> r.path("/api/auth/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://skilltrack-auth-service"))
                .route("skilltrack-user-service", r -> r.path(
                                "/api/user-profiles/**",
                                "/api/user-skills/**",
                                "/api/skills/**",
                                "/api/certifications/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://skilltrack-user-service"))
                .route("skilltrack-notification-service", r -> r.path(
                                "/api/notifications/**", "/api/preferences/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://skilltrack-notification-service"))
                .build();
    }
}
