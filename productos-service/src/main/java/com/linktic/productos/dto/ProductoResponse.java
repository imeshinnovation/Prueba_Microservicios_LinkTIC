package com.linktic.productos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {
    
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
        //@JsonProperty("type")
        //private String type = "productos";
        
        @JsonProperty("id")
        private String id;
        
        @JsonProperty("attributes")
        private Attributes attributes;

        public static class Attributes {
            private String nombre;
            private BigDecimal precio;
            private String descripcion;
            
            @JsonProperty("fecha_creacion")
            private LocalDateTime fechaCreacion;
            
            @JsonProperty("fecha_actualizacion")
            private LocalDateTime fechaActualizacion;

            // Getters and Setters
            public String getNombre() { return nombre; }
            public void setNombre(String nombre) { this.nombre = nombre; }
            public BigDecimal getPrecio() { return precio; }
            public void setPrecio(BigDecimal precio) { this.precio = precio; }
            public String getDescripcion() { return descripcion; }
            public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
            public LocalDateTime getFechaCreacion() { return fechaCreacion; }
            public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
            public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
            public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
        }

        //public String getType() { return type; }
        //public void setType(String type) { this.type = type; }
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public Attributes getAttributes() { return attributes; }
        public void setAttributes(Attributes attributes) { this.attributes = attributes; }
    }

    public Data getData() { return data; }
    public void setData(Data data) { this.data = data; }
}