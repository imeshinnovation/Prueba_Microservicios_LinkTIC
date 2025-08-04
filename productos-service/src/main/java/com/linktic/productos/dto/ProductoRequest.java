package com.linktic.productos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequest {
    
    @JsonProperty("data")
    private Data data;

    private Long id;
    private String nombre;
    private String categoria;
    private BigDecimal precio;
    private Integer stock;
    private String descripcion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public static class Data {
        
        @JsonProperty("attributes")
        private Attributes attributes;

        public static class Attributes {
            @NotBlank(message = "El nombre es obligatorio")
            private String nombre;
            
            @NotNull(message = "El precio es obligatorio")
            @Positive(message = "El precio debe ser positivo")
            private BigDecimal precio;
            
            private String descripcion;

            // Getters and Setters
            public String getNombre() { return nombre; }
            public void setNombre(String nombre) { this.nombre = nombre; }
            public BigDecimal getPrecio() { return precio; }
            public void setPrecio(BigDecimal precio) { this.precio = precio; }
            public String getDescripcion() { return descripcion; }
            public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        }

        public Attributes getAttributes() { return attributes; }
        public void setAttributes(Attributes attributes) { this.attributes = attributes; }
    }

    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }
}