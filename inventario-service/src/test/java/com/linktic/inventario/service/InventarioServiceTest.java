package com.linktic.inventario.service;

import com.linktic.inventario.client.ProductoClient;
import com.linktic.inventario.dto.ProductoClientResponse;
import com.linktic.inventario.exception.InventarioNotFoundException;
import com.linktic.inventario.model.Inventario;
import com.linktic.inventario.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private InventarioService inventarioService;

    private Inventario inventario;
    private ProductoClientResponse productoResponse;

    @BeforeEach
    void setUp() {
        inventario = new Inventario(1L, 10);
        inventario.setId(1L);

        productoResponse = new ProductoClientResponse();
        ProductoClientResponse.Data data = new ProductoClientResponse.Data();
        ProductoClientResponse.Data.Attributes attributes = new ProductoClientResponse.Data.Attributes();
        attributes.setNombre("Producto Test");
        attributes.setPrecio(new BigDecimal("99.99"));
        data.setId("1");
        data.setAttributes(attributes);
        productoResponse.setData(data);
    }

    @Test
    void obtenerInventarioPorProductoId_InventarioExiste_DeberiaRetornarInventario() {
        when(productoClient.obtenerProducto(1L)).thenReturn(productoResponse);
        when(inventarioRepository.findByProductoId(1L)).thenReturn(Optional.of(inventario));

        Inventario resultado = inventarioService.obtenerInventarioPorProductoId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getProductoId());
        assertEquals(10, resultado.getCantidad());
        verify(productoClient, times(1)).obtenerProducto(1L);
    }

    @Test
    void obtenerInventarioPorProductoId_InventarioNoExiste_DeberiaLanzarExcepcion() {
        when(productoClient.obtenerProducto(1L)).thenReturn(productoResponse);
        when(inventarioRepository.findByProductoId(1L)).thenReturn(Optional.empty());

        assertThrows(InventarioNotFoundException.class, () -> {
            inventarioService.obtenerInventarioPorProductoId(1L);
        });
    }

    @Test
    void reducirInventario_CantidadSuficiente_DeberiaReducirInventario() {
        when(productoClient.obtenerProducto(1L)).thenReturn(productoResponse);
        when(inventarioRepository.findByProductoId(1L)).thenReturn(Optional.of(inventario));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);

        inventarioService.reducirInventario(1L, 5);

        assertEquals(5, inventario.getCantidad());
        verify(inventarioRepository, times(1)).save(inventario);
    }

    @Test
    void reducirInventario_CantidadInsuficiente_DeberiaLanzarExcepcion() {
        when(productoClient.obtenerProducto(1L)).thenReturn(productoResponse);
        when(inventarioRepository.findByProductoId(1L)).thenReturn(Optional.of(inventario));

        assertThrows(IllegalArgumentException.class, () -> {
            inventarioService.reducirInventario(1L, 15);
        });
    }
}