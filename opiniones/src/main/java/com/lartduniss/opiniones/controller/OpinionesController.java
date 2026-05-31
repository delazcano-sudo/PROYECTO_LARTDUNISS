package com.lartduniss.opiniones.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lartduniss.opiniones.model.Opiniones;
import com.lartduniss.opiniones.service.OpinionesService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/opiniones")
public class OpinionesController 
{
    @Autowired
    private OpinionesService opinionesService;

    @GetMapping
    public List<Opiniones> listar() {
        return opinionesService.listarTodas();
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Opiniones>> obtenerPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(opinionesService.buscarPorProducto(productoId));
    }

    @GetMapping("/producto/{productoId}/promedio")
    public ResponseEntity<Double> obtenerPromedio(@PathVariable Long productoId) {
        return ResponseEntity.ok(opinionesService.obtenerPromedioCalificacion(productoId));
    }

    @PostMapping
    public ResponseEntity<Opiniones> crear(@Valid @RequestBody Opiniones opiniones) {
        Opiniones nuevaOpinion = opinionesService.guardar(opiniones);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOpinion);
    }
}