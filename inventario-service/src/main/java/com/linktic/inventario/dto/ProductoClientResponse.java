package com.linktic.inventario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public class ProductoClientResponse {
    
    @JsonProperty("data")
    private Data data;

    public static class Data {
        @JsonProperty("id")
        private String id;
        
        @JsonProperty("attributes")
        private Attributes attributes;

        public static class Attributes {
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

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public Attributes getAttributes() { return attributes; }
        public void setAttributes(Attributes attributes) { this.attributes = attributes; }
    }

    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }
}