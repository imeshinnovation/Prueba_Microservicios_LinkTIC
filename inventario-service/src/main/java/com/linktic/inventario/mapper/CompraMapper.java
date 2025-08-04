package com.linktic.inventario.mapper;

import com.linktic.inventario.dto.CompraResponse;
import com.linktic.inventario.model.HistorialCompra;
import org.springframework.stereotype.Component;

@Component
public class CompraMapper {

    public CompraResponse toResponse(HistorialCompra historialCompra, Integer inventarioRestante) {
        if (historialCompra == null) {
            return null;
        }

        CompraResponse response = new CompraResponse();
        CompraResponse.Data data = new CompraResponse.Data();
        CompraResponse.Data.Attributes attributes = new CompraResponse.Data.Attributes();

        attributes.setProductoId(historialCompra.getProductoId());
        attributes.setCantidad(historialCompra.getCantidad());
        attributes.setPrecioUnitario(historialCompra.getPrecioUnitario());
        attributes.setTotal(historialCompra.getTotal());
        attributes.setFechaCompra(historialCompra.getFechaCompra());
        attributes.setInventarioRestante(inventarioRestante);

        data.setId(historialCompra.getId().toString());
        data.setAttributes(attributes);
        response.setData(data);

        return response;
    }
}