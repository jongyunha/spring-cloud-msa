package org.jongyun.springcloudgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jongyunha on 3/11/22.
 */
//@Configuration
public class FilterConfig {

    /**
     * application yml 에서 설정해줬던 경로들을 자바 에서 해주기 위함
     */
//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder
            .routes()
            .route(r -> r.path("/first-service/**")
                .filters(f -> f.addRequestHeader("first-request", "first-request-header")
                    .addResponseHeader("first-response", "first-response-header")
                )
                .uri("http://localhost:8081"))
            .route(r -> r.path("/second-service/**")
                .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                    .addResponseHeader("second-response", "second-response-header")
                )
                .uri("http://localhost:8082"))
            .build();
    }

}
