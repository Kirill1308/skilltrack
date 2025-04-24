package com.skilltrack.common.client.config;

import feign.Request;
import feign.Retryer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableFeignClients(basePackages = "com.skilltrack.common.client")
public class FeignClientConfiguration {

    @Bean
    public Retryer retryer() {
        // Retry 3 times with a 100ms initial backoff, doubling each retry with max 1s delay
        return new Retryer.Default(100, TimeUnit.SECONDS.toMillis(1), 3);
    }

    @Bean
    public Request.Options options() {
        // 5 second connect timeout, 15 second read timeout
        return new Request.Options(5, TimeUnit.SECONDS, 15, TimeUnit.SECONDS, true);
    }
}
