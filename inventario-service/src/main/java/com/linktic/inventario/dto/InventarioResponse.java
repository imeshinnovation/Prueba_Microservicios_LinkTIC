package com.linktic.inventario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InventarioResponse {
    
    @JsonProperty("data")
    private Data data;

    public static class Data {
        
        @JsonProperty("id")
        private String id;
        
        @JsonProperty("attributes")
        private Attributes attributes;
        
        @JsonProperty("relationships")
        private Relationships relationships;

        public static class Attributes {
            @JsonProperty("producto_id")
            private Long productoId;
            
            private Integer cantidad;
            
            @JsonProperty("fecha_creacion")
            private LocalDateTime fechaCreacion;
            
            @JsonProperty("fecha_actualizacion")
            private LocalDateTime fechaActualizacion;

            // Getters and Setters
            public Long getProductoId() { return productoId; }
            public void setProductoId(Long productoId) { this.productoId = productoId; }
            public Integer getCantidad() { return cantidad; }
            public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
            public LocalDateTime getFechaCreacion() { return fechaCreacion; }
            public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
            public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
            public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
        }

        public static class Relationships {
            private ProductoData producto;

            public static class ProductoData {
                private ProductoInfo data;

                public static class ProductoInfo {
                    private String type = "productos";
                    private String id;
                    private ProductoAttributes attributes;

                    public static class ProductoAttributes {
                        private String nombre;
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

                    // Getters and Setters
                    public String getType() { return type; }
                    public void setType(String type) { this.type = type; }
                    public String getId() { return id; }
                    public void setId(String id) { this.id = id; }
                    public ProductoAttributes getAttributes() { return attributes; }
                    public void setAttributes(ProductoAttributes attributes) { this.attributes = attributes; }
                }

                public ProductoInfo getData() { return data; }
                public void setData(ProductoInfo data) { this.data = data; }
            }

            public ProductoData getProducto() { return producto; }
            public void setProducto(ProductoData producto) { this.producto = producto; }
        }

        // Getters and Setters
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public Attributes getAttributes() { return attributes; }
        public void setAttributes(Attributes attributes) { this.attributes = attributes; }
        public Relationships getRelationships() { return relationships; }
        public void setRelationships(Relationships relationships) { this.relationships = relationships; }
    }

    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }
}