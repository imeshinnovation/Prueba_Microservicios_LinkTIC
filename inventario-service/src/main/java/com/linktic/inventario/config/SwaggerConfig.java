package com.linktic.inventario.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {
    // Configuración adicional (opcional)
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Inventario")
                        .version("1.0")
                        .description("Documentación para el servicio de Inventario")
                        .license(new License().name("Apache 2.0")));
    }
}
