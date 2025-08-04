package com.linktic.productos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linktic.productos.dto.ProductoRequest;
import com.linktic.productos.dto.ProductoResponse;
import com.linktic.productos.model.Producto;
import com.linktic.productos.service.ProductoService;
import com.linktic.productos.mapper.ProductoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @Mock
    private ProductoMapper productoMapper;

    @InjectMocks
    private ProductoController productoController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void crearProducto_DeberiaRetornarProductoCreado_CuandoRequestEsValido() throws Exception {
        // Given - Preparar datos de prueba
        ProductoRequest request = ProductoRequest.builder()
                .nombre("Laptop Gaming")
                .descripcion("Laptop para gaming de alta gama")
                .precio(new BigDecimal("1500000"))
                .categoria("Tecnología")
                .stock(10)
                .build();

        Producto productoEntity = Producto.builder()
                .nombre("Laptop Gaming")
                .descripcion("Laptop para gaming de alta gama")
                .precio(new BigDecimal("1500000"))
                .categoria("Tecnología")
                .stock(10)
                .build();

        Producto productoCreado = Producto.builder()
                .id(1L)
                .nombre("Laptop Gaming")
                .descripcion("Laptop para gaming de alta gama")
                .precio(new BigDecimal("1500000"))
                .categoria("Tecnología")
                .stock(10)
                .build();

        ProductoResponse expectedResponse = ProductoResponse.builder()
                .id(1L)
                .nombre("Laptop Gaming")
                .descripcion("Laptop para gaming de alta gama")
                .precio(new BigDecimal("1500000"))
                .categoria("Tecnología")
                .stock(10)
                .build();

        // When - Configurar mocks
        when(productoMapper.toEntity(any(ProductoRequest.class))).thenReturn(productoEntity);
        when(productoService.crearProducto(any(Producto.class))).thenReturn(productoCreado);
        when(productoMapper.toResponse(any(Producto.class))).thenReturn(expectedResponse);

        // Then - Ejecutar y verificar
        mockMvc.perform(post("/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Laptop Gaming"))
                .andExpect(jsonPath("$.descripcion").value("Laptop para gaming de alta gama"))
                .andExpect(jsonPath("$.precio").value(1500000))
                .andExpect(jsonPath("$.categoria").value("Tecnología"))
                .andExpect(jsonPath("$.stock").value(10));

        // Verificar que los métodos fueron llamados correctamente
        verify(productoMapper, times(1)).toEntity(any(ProductoRequest.class));
        verify(productoService, times(1)).crearProducto(any(Producto.class));
        verify(productoMapper, times(1)).toResponse(any(Producto.class));
    }

    @Test
    void crearProducto_DeberiaRetornarBadRequest_CuandoRequestEsNulo() throws Exception {
        // Given - Request nulo (body vacío)
        
        // When & Then
        mockMvc.perform(post("/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());

        // Verificar que no se llamaron los servicios
        verify(productoMapper, never()).toEntity(any(ProductoRequest.class));
        verify(productoService, never()).crearProducto(any(Producto.class));
        verify(productoMapper, never()).toResponse(any(Producto.class));
    }

    @Test
    void crearProducto_DeberiaRetornarBadRequest_CuandoCamposObligatoriosFaltan() throws Exception {
        // Given - Request con campos faltantes
        ProductoRequest requestInvalido = ProductoRequest.builder()
                .descripcion("Solo descripción")
                .build();

        // When & Then
        mockMvc.perform(post("/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestInvalido)))
                .andExpect(status().isBadRequest());

        // Verificar que no se llamaron los servicios
        verify(productoMapper, never()).toEntity(any(ProductoRequest.class));
        verify(productoService, never()).crearProducto(any(Producto.class));
        verify(productoMapper, never()).toResponse(any(Producto.class));
    }

    @Test
    void crearProducto_DeberiaRetornarInternalServerError_CuandoServicioLanzaExcepcion() throws Exception {
        // Given
        ProductoRequest request = ProductoRequest.builder()
                .nombre("Producto Test")
                .descripcion("Descripción test")
                .precio(new BigDecimal("100000"))
                .categoria("Test")
                .stock(5)
                .build();

        Producto productoEntity = new Producto();
        
        when(productoMapper.toEntity(any(ProductoRequest.class))).thenReturn(productoEntity);
        when(productoService.crearProducto(any(Producto.class)))
                .thenThrow(new RuntimeException("Error interno del servicio"));

        // When & Then
        mockMvc.perform(post("/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());

        // Verificar interacciones
        verify(productoMapper, times(1)).toEntity(any(ProductoRequest.class));
        verify(productoService, times(1)).crearProducto(any(Producto.class));
        verify(productoMapper, never()).toResponse(any(Producto.class));
    }

    @Test
    void crearProducto_DeberiaRetornarUnsupportedMediaType_CuandoContentTypeEsIncorrecto() throws Exception {
        // Given
        ProductoRequest request = ProductoRequest.builder()
                .nombre("Producto Test")
                .build();

        // When & Then
        mockMvc.perform(post("/v1/productos")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnsupportedMediaType());

        // Verificar que no se llamaron los servicios
        verify(productoMapper, never()).toEntity(any(ProductoRequest.class));
        verify(productoService, never()).crearProducto(any(Producto.class));
        verify(productoMapper, never()).toResponse(any(Producto.class));
    }
}