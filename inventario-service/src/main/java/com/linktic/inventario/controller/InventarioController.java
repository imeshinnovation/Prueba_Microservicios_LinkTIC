package com.linktic.inventario.controller;

import com.linktic.inventario.dto.InventarioRequest;
import com.linktic.inventario.dto.InventarioResponse;
import com.linktic.inventario.mapper.InventarioMapper;
import com.linktic.inventario.model.Inventario;
import com.linktic.inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/inventarios")
@Tag(name = "Inventarios", description = "API para gestión de inventarios")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioMapper inventarioMapper;

    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Consultar inventario por ID de producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario encontrado"),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    public ResponseEntity<InventarioResponse> obtenerInventarioPorProducto(@PathVariable Long productoId) {
        Inventario inventario = inventarioService.obtenerInventarioPorProductoId(productoId);
        InventarioResponse response = inventarioMapper.toResponse(inventario, inventarioService.obtenerDetalleProducto(productoId));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/producto/{productoId}")
    @Operation(summary = "Crear inventario para un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Inventario creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o inventario ya existe"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<InventarioResponse> crearInventario(
            @PathVariable Long productoId,
            @Valid @RequestBody InventarioRequest request) {
        
        Integer cantidadInicial = request.getData().getAttributes().getCantidad();
        Inventario inventario = inventarioService.crearInventario(productoId, cantidadInicial);
        InventarioResponse response = inventarioMapper.toResponse(inventario, inventarioService.obtenerDetalleProducto(productoId));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/producto/{productoId}")
    @Operation(summary = "Actualizar cantidad de inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
    })
    public ResponseEntity<InventarioResponse> actualizarInventario(
            @PathVariable Long productoId,
            @Valid @RequestBody InventarioRequest request) {
        
        Integer nuevaCantidad = request.getData().getAttributes().getCantidad();
        Inventario inventario = inventarioService.actualizarInventario(productoId, nuevaCantidad);
        InventarioResponse response = inventarioMapper.toResponse(inventario, inventarioService.obtenerDetalleProducto(productoId));
        
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/producto/{productoId}/aumentar")
    @Operation(summary = "Aumentar cantidad de inventario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inventario aumentado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Inventario no encontrado"),
        @ApiResponse(responseCode = "400", description = "Cantidad inválida")
    })
    public ResponseEntity<InventarioResponse> aumentarInventario(
            @PathVariable Long productoId,
            @Valid @RequestBody InventarioRequest request) {
        
        Integer cantidad = request.getData().getAttributes().getCantidad();
        Inventario inventario = inventarioService.aumentarInventario(productoId, cantidad);
        InventarioResponse response = inventarioMapper.toResponse(inventario, inventarioService.obtenerDetalleProducto(productoId));
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/producto/{productoId}/exists")
    @Operation(summary = "Verificar si existe inventario para un producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Verificación completada")
    })
    public ResponseEntity<Boolean> existeInventario(@PathVariable Long productoId) {
        boolean existe = inventarioService.existeInventario(productoId);
        return ResponseEntity.ok(existe);
    }
}