package com.profcut.ordermanager.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
                .info(buildInfo());
    }

    private Info buildInfo() {
        Contact contact = new Contact();
        contact.setName("Илья Календарев");
        contact.setEmail("kilyaser@gmail.com");

        return new Info()
                .title("order-manager")
                .description("API order-manager")
                .contact(contact);
    }
}
