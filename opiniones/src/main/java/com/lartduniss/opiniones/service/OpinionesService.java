package com.lartduniss.opiniones.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;
import jakarta.transaction.Transactional;

import com.lartduniss.opiniones.model.Opiniones; 
import com.lartduniss.opiniones.repository.OpinionesRepository;

@Service
public class OpinionesService 
{
    @Autowired
    private OpinionesRepository opinionesRepository;

    public List<Opiniones> listarTodas() {
        return opinionesRepository.findAll();
    }

    // Método individual clave exigido por el patrón HATEOAS de la profe
    public Optional<Opiniones> buscarPorId(@NonNull Long id) {
        return opinionesRepository.findById(id);
    }

    public List<Opiniones> buscarPorProducto(@NonNull Long productoId) {
        return opinionesRepository.findByProductoId(productoId);
    }

    @Transactional // Mantenemos tu transacción de escritura en BD
    public Opiniones guardar(Opiniones opiniones) {
        opiniones.setFechaPublicacion(LocalDate.now());
        return opinionesRepository.save(opiniones);
    }

    // Mantenemos tu excelente método de promedio pero aplicando consistencia en el parámetro
    public Double obtenerPromedioCalificacion(@NonNull Long productoId) {
        List<Opiniones> listaOpiniones = opinionesRepository.findByProductoId(productoId);
        if (listaOpiniones.isEmpty()) {
            return 0.0;
        }
        
        double suma = listaOpiniones.stream()
                                    .mapToDouble(Opiniones::getCalificacion)
                                    .sum();
                                    
        return suma / listaOpiniones.size();
    }
}