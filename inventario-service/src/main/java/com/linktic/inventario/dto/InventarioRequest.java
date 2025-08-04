package com.linktic.inventario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class InventarioRequest {
    
    @JsonProperty("data")
    private Data data;

    public static class Data {
        
        @JsonProperty("attributes")
        private Attributes attributes;

        public static class Attributes {
            @NotNull(message = "El ID del producto es obligatorio")
            @JsonProperty("producto_id")
            private Long productoId;
            
            @NotNull(message = "La cantidad es obligatoria")
            @Min(value = 0, message = "La cantidad no puede ser negativa")
            private Integer cantidad;

            // Getters and Setters
            public Long getProductoId() { return productoId; }
            public void setProductoId(Long productoId) { this.productoId = productoId; }
            public Integer getCantidad() { return cantidad; }
            public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
        }

        public Attributes getAttributes() { return attributes; }
        public void setAttributes(Attributes attributes) { this.attributes = attributes; }
    }

    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }
}