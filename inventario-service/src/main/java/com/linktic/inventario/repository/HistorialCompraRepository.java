package com.linktic.inventario.repository;

import com.linktic.inventario.model.HistorialCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HistorialCompraRepository extends JpaRepository<HistorialCompra, Long> {
    List<HistorialCompra> findByProductoIdOrderByFechaCompraDesc(Long productoId);
}