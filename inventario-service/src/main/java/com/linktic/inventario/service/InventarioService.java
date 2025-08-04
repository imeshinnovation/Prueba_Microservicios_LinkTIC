package com.linktic.inventario.service;

import com.linktic.inventario.client.ProductoClient;
import com.linktic.inventario.dto.ProductoClientResponse;
import com.linktic.inventario.exception.InventarioNotFoundException;
import com.linktic.inventario.exception.ProductoServiceException;
import com.linktic.inventario.model.Inventario;
import com.linktic.inventario.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private ProductoClient productoClient;

    @Transactional(readOnly = true)
    public Inventario obtenerInventarioPorProductoId(Long productoId) {
        // Verificar que el producto existe
        try {
            productoClient.obtenerProducto(productoId);
        } catch (ProductoServiceException e) {
            throw e;
        }

        return inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new InventarioNotFoundException("Inventario para producto ID " + productoId + " no encontrado"));
    }

    @Transactional
    public Inventario crearInventario(Long productoId, Integer cantidadInicial) {
        // Validar que la cantidad inicial no sea negativa
        if (cantidadInicial < 0) {
            throw new IllegalArgumentException("La cantidad inicial no puede ser negativa");
        }

        // Verificar que el producto existe antes de crear el inventario
        try {
            productoClient.obtenerProducto(productoId);
        } catch (ProductoServiceException e) {
            throw new ProductoServiceException("No se puede crear inventario para un producto inexistente: " + e.getMessage());
        }

        // Verificar que no existe ya un inventario para este producto
        if (inventarioRepository.findByProductoId(productoId).isPresent()) {
            throw new IllegalArgumentException("Ya existe un inventario para el producto ID " + productoId);
        }

        // Crear y guardar el nuevo inventario
        Inventario nuevoInventario = new Inventario(productoId, cantidadInicial);
        return inventarioRepository.save(nuevoInventario);
    }

    @Transactional
    public Inventario actualizarInventario(Long productoId, Integer nuevaCantidad) {
        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElse(new Inventario(productoId, nuevaCantidad));
        
        inventario.setCantidad(nuevaCantidad);
        return inventarioRepository.save(inventario);
    }

    @Transactional
    public void reducirInventario(Long productoId, Integer cantidad) {
        Inventario inventario = obtenerInventarioPorProductoId(productoId);
        
        if (inventario.getCantidad() < cantidad) {
            throw new IllegalArgumentException("Inventario insuficiente. Disponible: " + inventario.getCantidad() + ", Solicitado: " + cantidad);
        }
        
        inventario.setCantidad(inventario.getCantidad() - cantidad);
        inventarioRepository.save(inventario);
    }

    @Transactional
    public Inventario aumentarInventario(Long productoId, Integer cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad a aumentar debe ser mayor que cero");
        }

        Inventario inventario = obtenerInventarioPorProductoId(productoId);
        inventario.setCantidad(inventario.getCantidad() + cantidad);
        return inventarioRepository.save(inventario);
    }

    @Transactional(readOnly = true)
    public boolean existeInventario(Long productoId) {
        return inventarioRepository.findByProductoId(productoId).isPresent();
    }

    @Transactional(readOnly = true)
    public ProductoClientResponse obtenerDetalleProducto(Long productoId) {
        return productoClient.obtenerProducto(productoId);
    }
}