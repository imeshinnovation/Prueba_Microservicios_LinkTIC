package com.linktic.productos.mapper;

import com.linktic.productos.dto.ProductoRequest;
import com.linktic.productos.dto.ProductoResponse;
import com.linktic.productos.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto toEntity(ProductoRequest request) {
        if (request == null) {
            return null;
        }

        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setDescripcion(request.getDescripcion());
        
        return producto;
    }

    public ProductoResponse toResponse(Producto producto) {
        if (producto == null) {
            return null;
        }

        ProductoResponse response = new ProductoResponse();
        response.setId(producto.getId());
        response.setNombre(producto.getNombre());
        response.setPrecio(producto.getPrecio());
        response.setDescripcion(producto.getDescripcion());
        response.setFechaCreacion(producto.getFechaCreacion());
        response.setFechaActualizacion(producto.getFechaActualizacion());
        
        return response;
    }
}