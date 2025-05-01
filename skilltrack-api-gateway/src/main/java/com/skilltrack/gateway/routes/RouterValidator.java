package com.skilltrack.gateway.routes;

import com.skilltrack.gateway.config.SecurityProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

@Service
@RequiredArgsConstructor
public class RouterValidator {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    private final SecurityProperties securityProperties;

    public boolean isSecuredRequest(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        return securityProperties.getOpenEndpoints().stream()
                .noneMatch(pattern -> pathMatcher.match(pattern, path));
    }
}
