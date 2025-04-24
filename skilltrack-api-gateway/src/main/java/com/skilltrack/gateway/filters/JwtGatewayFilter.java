package com.skilltrack.gateway.filters;

import com.skilltrack.gateway.routes.RouterValidator;
import com.skilltrack.jwt.JwtTokenValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
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
public class JwtGatewayFilter implements GatewayFilter {

    private final RouterValidator routerValidator;
    private final JwtTokenValidator jwtValidator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (routerValidator.isSecuredRequest(request)) {
            if (authMissing(request)) {
                return onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            try {
                String token = request.getHeaders().getFirst(AUTHORIZATION);
                if (!jwtValidator.isValid(token)) {
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }

                return chain.filter(exchange);

            } catch (Exception e) {
                log.error("Error processing JWT token", e);
                return onError(exchange, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return chain.filter(exchange);
    }

    private boolean authMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey(AUTHORIZATION);
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }
}
