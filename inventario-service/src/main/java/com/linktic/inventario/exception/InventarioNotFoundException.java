package com.linktic.inventario.exception;

public class InventarioNotFoundException extends RuntimeException {
    public InventarioNotFoundException(String message) {
        super(message);
    }
}