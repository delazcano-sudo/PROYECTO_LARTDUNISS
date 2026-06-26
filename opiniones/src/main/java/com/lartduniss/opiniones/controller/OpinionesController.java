package com.lartduniss.opiniones.controller;

import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.lartduniss.opiniones.model.Opiniones;
import com.lartduniss.opiniones.service.OpinionesService;

// IMPORTACIONES DE SWAGGER <3
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/v1/opiniones")
@Tag(name = "Controlador de Opiniones", description = "Endpoints para gestionar las reseñas y calificaciones de los productos")
public class OpinionesController {

    private final OpinionesService opinionesService;

    public OpinionesController(OpinionesService opinionesService) {
        this.opinionesService = opinionesService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las opiniones", description = "Retorna el historial global de reseñas almacenadas en la plataforma")
    @ApiResponse(responseCode = "200", description = "Lista de opiniones obtenida con éxito")
    public List<Opiniones> listar() {
        return opinionesService.listarTodas();
    }

    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Obtener opiniones por Producto", description = "Recupera todas las reseñas asociadas a un ID de producto específico")
    @ApiResponse(responseCode = "200", description = "Reseñas del producto localizadas exitosamente")
    @ApiResponse(responseCode = "404", description = "No se encontraron opiniones para el producto indicado", 
                 content = @Content(schema = @Schema(implementation = exception.ErrorResponse.class)))
    public ResponseEntity<List<Opiniones>> obtenerPorProducto(@PathVariable Long productoId) {
        List<Opiniones> opiniones = opinionesService.buscarPorProducto(productoId);
        if (opiniones.isEmpty()) {
            throw new RuntimeException("No se encontraron opiniones registradas para el producto con ID " + productoId);
        }
        return ResponseEntity.ok(opiniones);
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
        Opiniones nuevaOpinion = Objects.requireNonNull(opinionesService.guardar(opiniones), "La respuesta de guardar no puede ser null");
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOpinion);
    }
}