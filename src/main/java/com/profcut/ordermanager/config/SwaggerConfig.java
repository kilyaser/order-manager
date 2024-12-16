package com.profcut.ordermanager.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI orderManagerApiV1() {
        return new OpenAPI()
                .info(buildInfo())
                .servers(servers());
    }

    @Bean
    public GroupedOpenApi authApi() {
        return GroupedOpenApi.builder()
                .group("auth-api")
                .packagesToScan("com.profcut.ordermanager.controllers.rest.auth")
                .build();
    }

    @Bean
    public GroupedOpenApi uiApi() {
        return GroupedOpenApi.builder()
                .group("ui-common-api")
                .packagesToScan("com.profcut.ordermanager.controllers.rest.ui")
                .build();
    }

    private Info buildInfo() {
        Contact contact = new Contact();
        contact.setName("Илья Календарев");
        contact.setEmail("kilyaser@gmail.com");

        return new Info()
                .title("order-manager")
                .version("1.0.0")
                .description("API order-manager")
                .contact(contact);
    }

    private List<Server> servers() {
        final Server domain = new Server()
                .url("http://order-manager.ru")
                .description("dev server");

        final Server ipServer = new Server()
                .url("http://62.113.104.3:8081")
                .description("id address server");

        final Server local = new Server()
                .url("http://localhost:8081")
                .description("localhost server");

        return List.of(domain, ipServer, local);
    }
}
