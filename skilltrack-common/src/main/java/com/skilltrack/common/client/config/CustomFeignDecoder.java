package com.skilltrack.common.client.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.skilltrack.common.exception.ApiError;
import com.skilltrack.common.exception.BaseException;
import com.skilltrack.common.exception.ErrorCode;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomFeignDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();
    private final ObjectMapper objectMapper;

    public CustomFeignDecoder() {
        this.objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            ApiError apiError = extractApiError(response);

            if (apiError != null) {
                log.debug("Parsed API error from response: {}", apiError);

                return new BaseException(ErrorCode.valueOf(apiError.getStatusName()), apiError.getMessage());
            }
        } catch (Exception e) {
            log.warn("Error parsing error response from service: {}", e.getMessage());
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }

    private ApiError extractApiError(Response response) {
        try {
            if (response.body() != null) {
                try (InputStream inputStream = response.body().asInputStream()) {
                    String responseBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

                    log.debug("Response body: {}", responseBody);

                    if (!responseBody.isEmpty()) {
                        return objectMapper.readValue(responseBody, ApiError.class);
                    }
                }
            }
        } catch (IOException e) {
            log.warn("Failed to extract error details from response: {}", e.getMessage());
        }
        return null;
    }

}
