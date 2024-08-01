package com.e_commerce.E_commerce.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "E-commerce",
                version = "1.3",
                description = "CRUD of clients, product, carts and invoices in e-commerce"
        )
)
public class OpenApi {
}
