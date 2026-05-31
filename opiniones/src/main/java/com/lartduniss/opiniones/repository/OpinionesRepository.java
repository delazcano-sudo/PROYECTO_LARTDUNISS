package com.lartduniss.opiniones.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.lartduniss.opiniones.model.Opiniones;

public interface OpinionesRepository extends JpaRepository<Opiniones, Long>
{
    List<Opiniones> findByProductoId(Long productoId);
}
