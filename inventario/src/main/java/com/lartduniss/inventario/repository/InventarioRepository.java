package com.lartduniss.inventario.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lartduniss.inventario.model.Inventario;

public interface InventarioRepository extends JpaRepository<Inventario, Long>
{
    Optional<Inventario> findByProductoId(Long productoId);
}
