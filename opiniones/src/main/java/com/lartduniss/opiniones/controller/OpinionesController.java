package com.lartduniss.opiniones.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

import com.lartduniss.opiniones.model.Opiniones;
import com.lartduniss.opiniones.service.OpinionesService;

// IMPORTACIONES DE SWAGGER <3
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/opiniones")
@Tag(name = "Controlador de Opiniones", description = "Endpoints para gestionar las reseñas y calificaciones de los productos")
public class OpinionesController {

    @Autowired
    private OpinionesService opinionesService;

    @GetMapping
    @Operation(summary = "Listar todas las opiniones", description = "Retorna el historial global de reseñas almacenadas en la plataforma")
    @ApiResponse(responseCode = "200", description = "Lista de opiniones obtenida con éxito")
    public List<Opiniones> listar() {
        return opinionesService.listarTodas();
    }

    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Obtener opiniones por Producto", description = "Recupera todas las reseñas asociadas a un ID de producto específico")
    @ApiResponse(responseCode = "200", description = "Reseñas del producto localizadas exitosamente")
    public ResponseEntity<List<Opiniones>> obtenerPorProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(opinionesService.buscarPorProducto(productoId));
    }

    @GetMapping("/producto/{productoId}/promedio")
    @Operation(summary = "Calcular promedio de calificación", description = "Genera la puntuación media en base a las estrellas asignadas al producto")
    @ApiResponse(responseCode = "200", description = "Promedio calculado correctamente (retorna 0.0 si no registra opiniones)")
    public ResponseEntity<Double> obtenerPromedio(@PathVariable Long productoId) {
        return ResponseEntity.ok(opinionesService.obtenerPromedioCalificacion(productoId));
    }

    @PostMapping
    @Operation(summary = "Publicar una opinión", description = "Registra una nueva opinión asignándole automáticamente la fecha actual")
    @ApiResponse(responseCode = "201", description = "Opinión publicada correctamente")
    @ApiResponse(responseCode = "400", description = "Cuerpo de petición no válido (por ejemplo, calificación fuera del rango 1-5)")
    public ResponseEntity<Opiniones> crear(@Valid @RequestBody Opiniones opiniones) {
        Opiniones nuevaOpinion = opinionesService.guardar(opiniones);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOpinion);
    }
}