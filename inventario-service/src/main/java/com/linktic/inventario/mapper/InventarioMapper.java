package com.linktic.inventario.mapper;

import com.linktic.inventario.dto.InventarioResponse;
import com.linktic.inventario.dto.ProductoClientResponse;
import com.linktic.inventario.model.Inventario;
import org.springframework.stereotype.Component;

@Component
public class InventarioMapper {

    public InventarioResponse toResponse(Inventario inventario, ProductoClientResponse productoResponse) {
        if (inventario == null) {
            return null;
        }

        InventarioResponse response = new InventarioResponse();
        InventarioResponse.Data data = new InventarioResponse.Data();
        InventarioResponse.Data.Attributes attributes = new InventarioResponse.Data.Attributes();

        attributes.setProductoId(inventario.getProductoId());
        attributes.setCantidad(inventario.getCantidad());
        attributes.setFechaCreacion(inventario.getFechaCreacion());
        attributes.setFechaActualizacion(inventario.getFechaActualizacion());

        data.setId(inventario.getId().toString());
        data.setAttributes(attributes);

        // Agregar relación con producto
        if (productoResponse != null && productoResponse.getData() != null) {
            InventarioResponse.Data.Relationships relationships = new InventarioResponse.Data.Relationships();
            InventarioResponse.Data.Relationships.ProductoData productoData = new InventarioResponse.Data.Relationships.ProductoData();
            InventarioResponse.Data.Relationships.ProductoData.ProductoInfo productoInfo = new InventarioResponse.Data.Relationships.ProductoData.ProductoInfo();
            InventarioResponse.Data.Relationships.ProductoData.ProductoInfo.ProductoAttributes productoAttrs = new InventarioResponse.Data.Relationships.ProductoData.ProductoInfo.ProductoAttributes();

            productoAttrs.setNombre(productoResponse.getData().getAttributes().getNombre());
            productoAttrs.setPrecio(productoResponse.getData().getAttributes().getPrecio());
            productoAttrs.setDescripcion(productoResponse.getData().getAttributes().getDescripcion());

            productoInfo.setId(productoResponse.getData().getId());
            productoInfo.setAttributes(productoAttrs);
            productoData.setData(productoInfo);
            relationships.setProducto(productoData);
            data.setRelationships(relationships);
        }

        response.setData(data);
        return response;
    }
}