package com.linktic.productos.controller;

import com.linktic.productos.dto.ProductoRequest;
import com.linktic.productos.dto.ProductoResponse;
import com.linktic.productos.model.Producto;
import com.linktic.productos.service.ProductoService;
import com.linktic.productos.mapper.ProductoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/productos")
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoMapper productoMapper;

    @PostMapping
    @Operation(summary = "Crear un nuevo producto")
    public ResponseEntity<ProductoResponse> crearProducto(@Valid @RequestBody ProductoRequest request) {
        Producto producto = productoMapper.toEntity(request);
        Producto productoCreado = productoService.crearProducto(producto);
        ProductoResponse response = productoMapper.toResponse(productoCreado);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public ResponseEntity<ProductoResponse> obtenerProducto(@PathVariable Long id) {
        Producto producto = productoService.obtenerProductoPorId(id);
        ProductoResponse response = productoMapper.toResponse(producto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos")
    public ResponseEntity<List<ProductoResponse>> obtenerTodosLosProductos() {
        List<Producto> productos = productoService.obtenerTodosLosProductos();
        List<ProductoResponse> responses = productos.stream()
                .map(productoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto por ID")
    public ResponseEntity<ProductoResponse> actualizarProducto(
            @PathVariable Long id,
            @RequestBody ProductoRequest request) {
        Producto productoActualizado = productoMapper.toEntity(request);
        Producto resultado = productoService.actualizarProducto(id, productoActualizado);
        ProductoResponse response = productoMapper.toResponse(resultado);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto por ID")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build(); // HTTP 204
    }

}