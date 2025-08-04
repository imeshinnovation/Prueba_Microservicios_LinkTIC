package com.linktic.inventario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CompraRequest {
    
    @JsonProperty("data")
    private Data data;

    public static class Data {
        @JsonProperty("type")
        private String type = "compras";
        
        @JsonProperty("attributes")
        private Attributes attributes;

        public static class Attributes {
            @NotNull(message = "El ID del producto es obligatorio")
            @JsonProperty("producto_id")
            private Long productoId;
            
            @NotNull(message = "La cantidad es obligatoria")
            @Min(value = 1, message = "La cantidad debe ser mayor a 0")
            private Integer cantidad;

            // Getters and Setters
            public Long getProductoId() { return productoId; }
            public void setProductoId(Long productoId) { this.productoId = productoId; }
            public Integer getCantidad() { return cantidad; }
            public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
        }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public Attributes getAttributes() { return attributes; }
        public void setAttributes(Attributes attributes) { this.attributes = attributes; }
    }

    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }
}