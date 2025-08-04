package com.linktic.inventario.service;

import com.linktic.inventario.client.ProductoClient;
import com.linktic.inventario.dto.ProductoClientResponse;
import com.linktic.inventario.model.HistorialCompra;
import com.linktic.inventario.repository.HistorialCompraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    private InventarioService inventarioService;

    @Mock
    private HistorialCompraRepository historialCompraRepository;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private CompraService compraService;

    private ProductoClientResponse productoResponse;

    @BeforeEach
    void setUp() {
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
    void procesarCompra_CompraExitosa_DeberiaRetornarHistorialCompra() {
        Long productoId = 1L;
        Integer cantidad = 2;
        HistorialCompra historialEsperado = new HistorialCompra(productoId, cantidad, 
                new BigDecimal("99.99"), new BigDecimal("199.98"));
        historialEsperado.setId(1L);

        when(productoClient.obtenerProducto(productoId)).thenReturn(productoResponse);
        doNothing().when(inventarioService).reducirInventario(productoId, cantidad);
        when(historialCompraRepository.save(any(HistorialCompra.class))).thenReturn(historialEsperado);

        HistorialCompra resultado = compraService.procesarCompra(productoId, cantidad);

        assertNotNull(resultado);
        assertEquals(productoId, resultado.getProductoId());
        assertEquals(cantidad, resultado.getCantidad());
        assertEquals(new BigDecimal("99.99"), resultado.getPrecioUnitario());
        assertEquals(new BigDecimal("199.98"), resultado.getTotal());

        verify(productoClient, times(1)).obtenerProducto(productoId);
        verify(inventarioService, times(1)).reducirInventario(productoId, cantidad);
        verify(historialCompraRepository, times(1)).save(any(HistorialCompra.class));
    }
}