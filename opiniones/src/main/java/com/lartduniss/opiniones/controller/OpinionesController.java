package com.lartduniss.opiniones.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import com.lartduniss.opiniones.model.Opiniones;
import com.lartduniss.opiniones.service.OpinionesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/opiniones")
@Tag(name = "Opiniones", description = "Operaciones relacionadas con la gestión de reseñas y calificaciones con soporte HATEOAS")
@SuppressWarnings("null")
public class OpinionesController 
{
    @Autowired
    private OpinionesService opinionesService;

    @Operation(summary = "Obtener todas las opiniones", description = "Retorna una colección de todas las reseñas del sistema con soporte hipermedia")
    @GetMapping
    public CollectionModel<EntityModel<Opiniones>> listar() 
    {
        List<EntityModel<Opiniones>> opiniones = opinionesService.listarTodas().stream()
                .map(opinion -> EntityModel.of(opinion,
                        linkTo(methodOn(OpinionesController.class).obtenerPorId(opinion.getId())).withSelfRel(),
                        linkTo(methodOn(OpinionesController.class).listar()).withRel("opiniones")))
                .collect(Collectors.toList());

        return CollectionModel.of(opiniones,
                linkTo(methodOn(OpinionesController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener opinión por ID", description = "Retorna una reseña individual basada en su ID único autoincremental")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Opinión encontrada con éxito"),
        @ApiResponse(responseCode = "404", description = "La opinión solicitada no existe")
    })
    @GetMapping("/{id}")
    public EntityModel<Opiniones> obtenerPorId(@NonNull @PathVariable Long id) 
    {
        Opiniones opinion = opinionesService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Opinión no encontrada con el ID: " + id));

        return EntityModel.of(opinion,
                linkTo(methodOn(OpinionesController.class).obtenerPorId(id)).withSelfRel(),
                linkTo(methodOn(OpinionesController.class).listar()).withRel("todas-las-opiniones"),
                linkTo(methodOn(OpinionesController.class).obtenerPorProducto(opinion.getProductoId())).withRel("opiniones-del-mismo-producto"));
    }

    @Operation(summary = "Obtener opiniones por ID de Producto", description = "Retorna una lista de reseñas asociadas exclusivamente a un cupcake específico")
    @GetMapping("/producto/{productoId}")
    public CollectionModel<EntityModel<Opiniones>> obtenerPorProducto(@NonNull @PathVariable Long productoId) 
    {
        List<EntityModel<Opiniones>> opiniones = opinionesService.buscarPorProducto(productoId).stream()
                .map(opinion -> EntityModel.of(opinion,
                        linkTo(methodOn(OpinionesController.class).obtenerPorId(opinion.getId())).withSelfRel()))
                .collect(Collectors.toList());

        return CollectionModel.of(opiniones,
                linkTo(methodOn(OpinionesController.class).obtenerPorProducto(productoId)).withSelfRel(),
                linkTo(methodOn(OpinionesController.class).obtenerPromedio(productoId)).withRel("promedio-calificacion"));
    }

    @Operation(summary = "Obtener promedio de calificación de un producto", description = "Calcula y retorna la nota media de estrellas (1 a 5) para un producto determinado")
    @GetMapping("/producto/{productoId}/promedio")
    public ResponseEntity<EntityModel<Double>> obtenerPromedio(@NonNull @PathVariable Long productoId) 
    {
        Double promedio = opinionesService.obtenerPromedioCalificacion(productoId);
        
        EntityModel<Double> recurso = EntityModel.of(promedio,
                linkTo(methodOn(OpinionesController.class).obtenerPromedio(productoId)).withSelfRel(),
                linkTo(methodOn(OpinionesController.class).obtenerPorProducto(productoId)).withRel("ver-opiniones"));
                
        return ResponseEntity.ok(recurso);
    }

    @Operation(summary = "Crear una nueva opinión", description = "Registra una reseña y asigna de manera automática la fecha de publicación actual")
    @PostMapping
    public ResponseEntity<EntityModel<Opiniones>> crear(@NonNull @Valid @RequestBody Opiniones opiniones) 
    {
        Opiniones nuevaOpinion = opinionesService.guardar(opiniones);
        
        EntityModel<Opiniones> recurso = EntityModel.of(nuevaOpinion,
                linkTo(methodOn(OpinionesController.class).obtenerPorId(nuevaOpinion.getId())).withSelfRel(),
                linkTo(methodOn(OpinionesController.class).obtenerPorProducto(nuevaOpinion.getProductoId())).withRel("opiniones-del-producto"));
                
        return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
    }
}