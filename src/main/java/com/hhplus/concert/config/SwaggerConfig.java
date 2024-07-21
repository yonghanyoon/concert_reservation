package com.hhplus.concert.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI concertAPI() {
        return new OpenAPI()
            .addServersItem(new Server().url("http://localhost:8080").description("local(8080)"))
            .info(new Info()
                      .title("HangHae+ Concert Reservation API")
                      .description("Concert Reservation API 명세서")
                      .version("1.0.0"));
    }

    @Bean
    public GroupedOpenApi authApiGroup() {
        return GroupedOpenApi.builder()
                             .group("1. Token")
                             .displayName("Token")
                             .pathsToMatch("/api/token/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi balanceApiGroup() {
        return GroupedOpenApi.builder()
                             .group("2. Balance")
                             .displayName("Balance")
                             .pathsToMatch("/api/balance/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi concertApiGroup() {
        return GroupedOpenApi.builder()
                             .group("3. Concert")
                             .displayName("Concert")
                             .pathsToMatch("/api/concerts/**")
                             .build();
    }

    @Bean
    public GroupedOpenApi reservationApiGroup() {
        return GroupedOpenApi.builder()
                             .group("4. Reservation")
                             .displayName("Reservation")
                             .pathsToMatch("/api/reservations/**")
                             .build();
    }
}
