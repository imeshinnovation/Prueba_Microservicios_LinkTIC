package com.linktic.inventario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CompraResponse {
    
    @JsonProperty("data")
    private Data data;

    public static class Data {
        @JsonProperty("type")
        private String type = "compras";
        
        @JsonProperty("id")
        private String id;
        
        @JsonProperty("attributes")
        private Attributes attributes;

        public static class Attributes {
            @JsonProperty("producto_id")
            private Long productoId;
            
            private Integer cantidad;
            
            @JsonProperty("precio_unitario")
            private BigDecimal precioUnitario;
            
            private BigDecimal total;
            
            @JsonProperty("fecha_compra")
            private LocalDateTime fechaCompra;
            
            @JsonProperty("inventario_restante")
            private Integer inventarioRestante;

            // Getters and Setters
            public Long getProductoId() { return productoId; }
            public void setProductoId(Long productoId) { this.productoId = productoId; }
            public Integer getCantidad() { return cantidad; }
            public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
            public BigDecimal getPrecioUnitario() { return precioUnitario; }
            public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
            public BigDecimal getTotal() { return total; }
            public void setTotal(BigDecimal total) { this.total = total; }
            public LocalDateTime getFechaCompra() { return fechaCompra; }
            public void setFechaCompra(LocalDateTime fechaCompra) { this.fechaCompra = fechaCompra; }
            public Integer getInventarioRestante() { return inventarioRestante; }
            public void setInventarioRestante(Integer inventarioRestante) { this.inventarioRestante = inventarioRestante; }
        }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public Attributes getAttributes() { return attributes; }
        public void setAttributes(Attributes attributes) { this.attributes = attributes; }
    }

    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }
}