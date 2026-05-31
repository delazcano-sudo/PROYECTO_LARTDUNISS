package com.lartduniss.opiniones.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lartduniss.opiniones.model.Opiniones; 
import com.lartduniss.opiniones.repository.OpinionesRepository;
import jakarta.transaction.Transactional;

@Service
public class OpinionesService 
{
    @Autowired
    private OpinionesRepository opinionesRepository;

    public List<Opiniones> listarTodas() {
        return opinionesRepository.findAll();
    }

    public List<Opiniones> buscarPorProducto(Long productoId) {
        return opinionesRepository.findByProductoId(productoId);
    }

    @Transactional
    public Opiniones guardar(Opiniones opiniones) {
        opiniones.setFechaPublicacion(LocalDate.now());
        return opinionesRepository.save(opiniones);
    }

    public Double obtenerPromedioCalificacion(Long productoId) {
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