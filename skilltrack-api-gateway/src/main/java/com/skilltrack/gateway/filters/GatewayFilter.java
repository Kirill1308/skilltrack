package com.skilltrack.gateway.filters;

import com.skilltrack.gateway.jwt.JwtTokenValidator;
import com.skilltrack.gateway.routes.RouterValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RefreshScope
@Component
@RequiredArgsConstructor
public class GatewayFilter implements GlobalFilter {

    private final RouterValidator routerValidator;
    private final JwtTokenValidator jwtValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();
        String method = request.getMethod().toString();

        log.info("Processing request - Method: {}, Path: {}", method, path);

        if (routerValidator.isSecuredRequest(request)) {
            log.debug("Request requires authentication - Path: {}", path);

            if (authMissing(request)) {
                log.warn("Authorization header missing for secured endpoint - Path: {}", path);
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            try {
                String token = request.getHeaders().getFirst(AUTHORIZATION);

                if (!jwtValidator.isValid(token)) {
                    log.warn("Invalid JWT token for path: {}", path);
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }

                log.info("Successfully validated JWT token for path: {}", path);
                return chain.filter(exchange);

            } catch (Exception e) {
                log.error("Error processing JWT token for path: {} - Error: {}", path, e.getMessage(), e);
                return onError(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        log.debug("Request does not require authentication - Path: {}", path);
        return chain.filter(exchange);
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(AUTHORIZATION);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);

        log.error("Returning error response - Status: {}, Path: {}", status, request.getPath());

        return response.setComplete();
    }
}
