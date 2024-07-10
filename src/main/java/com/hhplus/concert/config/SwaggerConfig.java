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
    public OperationCustomizer customizer() {
        return ((operation, handlerMethod) -> {
           operation.addParametersItem(
               new Parameter()
                   .in("header")
                   .required(true)
                   .description("사용자 UUID")
           );
           return operation;
        });
    }

    @Bean
    public GroupedOpenApi authApiGroup() {
        return GroupedOpenApi.builder()
                             .group("1. Auth")
                             .displayName("Auth")
                             .pathsToMatch("/api/auth/**")
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
    public GroupedOpenApi concertApiGroup(OperationCustomizer customizer) {
        return GroupedOpenApi.builder()
                             .group("3. Concert")
                             .displayName("Concert")
                             .pathsToMatch("/api/concerts/**")
                             .addOperationCustomizer(customizer)
                             .build();
    }

    @Bean
    public GroupedOpenApi paymentApiGroup(OperationCustomizer customizer) {
        return GroupedOpenApi.builder()
                             .group("4. Payment")
                             .displayName("Payment")
                             .pathsToMatch("/api/payments/**")
                             .addOperationCustomizer(customizer)
                             .build();
    }

    @Bean
    public GroupedOpenApi reservationApiGroup(OperationCustomizer customizer) {
        return GroupedOpenApi.builder()
                             .group("5. Reservation")
                             .displayName("Reservation")
                             .pathsToMatch("/api/reservations/**")
                             .addOperationCustomizer(customizer)
                             .build();
    }
}
