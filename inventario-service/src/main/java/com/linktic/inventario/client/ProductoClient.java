package com.linktic.inventario.client;

import com.linktic.inventario.dto.ProductoClientResponse;
import com.linktic.inventario.exception.ProductoServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class ProductoClient {

    private final WebClient webClient;
    private final String productosApiKey;

    public ProductoClient(@Value("${productos.service.url}") String productosServiceUrl,
                         @Value("${productos.service.api-key}") String productosApiKey) {
        this.productosApiKey = productosApiKey;
        this.webClient = WebClient.builder()
                .baseUrl(productosServiceUrl)
                .build();
    }

    public ProductoClientResponse obtenerProducto(Long productoId) {
        try {
            return webClient.get()
                    .uri("/api/v1/productos/{id}", productoId)
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .header("X-API-Key", productosApiKey)
                    .retrieve()
                    .bodyToMono(ProductoClientResponse.class)
                    .retryWhen(Retry.backoff(3, Duration.ofSeconds(1)))
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (WebClientResponseException e) {
            if (e.getStatusCode().value() == 404) {
                throw new ProductoServiceException("Producto con ID " + productoId + " no encontrado");
            }
            throw new ProductoServiceException("Error al obtener producto: " + e.getMessage());
        } catch (Exception e) {
            throw new ProductoServiceException("Error de comunicación con el servicio de productos: " + e.getMessage());
        }
    }
}