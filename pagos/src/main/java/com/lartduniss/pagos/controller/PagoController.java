package com.lartduniss.pagos.controller;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lartduniss.pagos.model.Pago;
import com.lartduniss.pagos.service.PagoService;

import io.micrometer.common.lang.NonNull;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Controlador para la gestión de pagos de pedidos")
@SuppressWarnings("null")
public class PagoController 
{
    @Autowired
    private PagoService pagoService;
    
    @Operation(summary = "Obtener todos los pagos", description = "Retorna una colección de pagos con sus respectivos enlaces hipermedia")
    @GetMapping
    public CollectionModel<EntityModel<Pago>> listar() 
    {
        List<EntityModel<Pago>> pagos = pagoService.listarTodos().stream()
                .map(pago -> EntityModel.of(pago,
                        linkTo(methodOn(PagoController.class).obtener(pago.getId())).withSelfRel(),
                        linkTo(methodOn(PagoController.class).listar()).withRel("pagos")))
                .collect(Collectors.toList());

        return CollectionModel.of(pagos,
                linkTo(methodOn(PagoController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener pago por ID", description = "Retorna un pago individual con enlaces a acciones relacionadas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago encontrado"),
        @ApiResponse(responseCode = "404", description = "El registro de pago no existe")
    })
    @GetMapping("/{id}")
    public EntityModel<Pago> obtener(@NonNull @PathVariable Long id) 
    {
        // Usamos orElseThrow para seguir el flujo de control de excepciones
        Pago pago = pagoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        return EntityModel.of(pago,
                linkTo(methodOn(PagoController.class).obtener(id)).withSelfRel(),
                linkTo(methodOn(PagoController.class).listar()).withRel("todos-los-pagos"),
                linkTo(methodOn(PagoController.class).eliminar(id)).withRel("eliminar"));
    }

    @Operation(summary = "Crear un nuevo registro de pago", description = "Procesa el monto y notifica al microservicio de Pedidos")
    @PostMapping
    public ResponseEntity<EntityModel<Pago>> crear(@NonNull @Valid @RequestBody Pago pago) 
    {
        Pago nuevoPago = pagoService.guardar(pago);
        
        EntityModel<Pago> recurso = EntityModel.of(nuevoPago,
                linkTo(methodOn(PagoController.class).obtener(nuevoPago.getId())).withSelfRel(),
                linkTo(methodOn(PagoController.class).listar()).withRel("todos-los-pagos"));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
    }

    @Operation(summary = "Eliminar un registro de pago")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@NonNull @PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
