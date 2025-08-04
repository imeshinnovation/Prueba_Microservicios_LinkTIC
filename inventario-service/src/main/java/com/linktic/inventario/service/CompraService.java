package com.linktic.inventario.service;

import com.linktic.inventario.client.ProductoClient;
import com.linktic.inventario.dto.ProductoClientResponse;
import com.linktic.inventario.model.HistorialCompra;
import com.linktic.inventario.model.Inventario;
import com.linktic.inventario.repository.HistorialCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CompraService {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private HistorialCompraRepository historialCompraRepository;

    @Autowired
    private ProductoClient productoClient;

    @Transactional
    public HistorialCompra procesarCompra(Long productoId, Integer cantidad) {
        // 1. Obtener información del producto
        ProductoClientResponse productoResponse = productoClient.obtenerProducto(productoId);
        BigDecimal precioUnitario = productoResponse.getData().getAttributes().getPrecio();

        // 2. Verificar inventario disponible y reducir
        inventarioService.reducirInventario(productoId, cantidad);

        // 3. Calcular total
        BigDecimal total = precioUnitario.multiply(BigDecimal.valueOf(cantidad));

        // 4. Registrar en historial
        HistorialCompra historialCompra = new HistorialCompra(productoId, cantidad, precioUnitario, total);
        return historialCompraRepository.save(historialCompra);
    }

    public Integer obtenerInventarioRestante(Long productoId) {
        try {
            Inventario inventario = inventarioService.obtenerInventarioPorProductoId(productoId);
            return inventario.getCantidad();
        } catch (Exception e) {
            return 0;
        }
    }
}