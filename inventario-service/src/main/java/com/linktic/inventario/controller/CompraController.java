package com.linktic.inventario.controller;

import com.linktic.inventario.dto.CompraRequest;
import com.linktic.inventario.dto.CompraResponse;
import com.linktic.inventario.mapper.CompraMapper;
import com.linktic.inventario.model.HistorialCompra;
import com.linktic.inventario.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/compras")
@Tag(name = "Compras", description = "API para procesamiento de compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Autowired
    private CompraMapper compraMapper;

    @PostMapping
    @Operation(summary = "Procesar una compra")
    public ResponseEntity<CompraResponse> procesarCompra(@Valid @RequestBody CompraRequest request) {
        Long productoId = request.getData().getAttributes().getProductoId();
        Integer cantidad = request.getData().getAttributes().getCantidad();
        
        HistorialCompra historialCompra = compraService.procesarCompra(productoId, cantidad);
        Integer inventarioRestante = compraService.obtenerInventarioRestante(productoId);
        
        CompraResponse response = compraMapper.toResponse(historialCompra, inventarioRestante);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}