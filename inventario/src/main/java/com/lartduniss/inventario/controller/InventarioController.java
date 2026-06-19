package com.lartduniss.inventario.controller;

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

import com.lartduniss.inventario.model.Inventario;
import com.lartduniss.inventario.service.InventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "Operaciones relacionadas con el control de stock y existencias con soporte HATEOAS")
@SuppressWarnings("null")
public class InventarioController 
{
    @Autowired
    private InventarioService inventarioService;

    @Operation(summary = "Obtener todo el inventario", description = "Retorna una colección de todos los registros de existencias con sus respectivos enlaces hipermedia")
    @GetMapping
    public CollectionModel<EntityModel<Inventario>> listar() 
    {
        List<EntityModel<Inventario>> inventarios = inventarioService.listarTodo().stream()
                .map(inventario -> EntityModel.of(inventario,
                        linkTo(methodOn(InventarioController.class).obtenerPorId(inventario.getId())).withSelfRel(),
                        linkTo(methodOn(InventarioController.class).listar()).withRel("inventarios")))
                .collect(Collectors.toList());

        return CollectionModel.of(inventarios,
                linkTo(methodOn(InventarioController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Obtener registro de inventario por ID", description = "Retorna un registro de stock individual basado en su ID único incremental")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Registro de inventario encontrado"),
        @ApiResponse(responseCode = "404", description = "El registro de inventario no existe")
    })
    @GetMapping("/{id}")
    public EntityModel<Inventario> obtenerPorId(@NonNull @PathVariable Long id) 
    {
        Inventario inventario = inventarioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Registro de inventario no encontrado"));

        return EntityModel.of(inventario,
                linkTo(methodOn(InventarioController.class).obtenerPorId(id)).withSelfRel(),
                linkTo(methodOn(InventarioController.class).listar()).withRel("todos-los-inventarios"),
                linkTo(methodOn(InventarioController.class).obtenerPorProducto(inventario.getProductoId())).withRel("buscar-por-producto"));
    }

    @Operation(summary = "Obtener inventario por ID de Producto", description = "Retorna la información de existencias asociada exclusivamente a un producto específico")
    @GetMapping("/producto/{productoId}")
    public EntityModel<Inventario> obtenerPorProducto(@NonNull @PathVariable Long productoId) 
    {
        Inventario inventario = inventarioService.buscarPorProducto(productoId)
                .orElseThrow(() -> new RuntimeException("No se encontró inventario para el producto con ID: " + productoId));

        return EntityModel.of(inventario,
                linkTo(methodOn(InventarioController.class).obtenerPorProducto(productoId)).withSelfRel(),
                linkTo(methodOn(InventarioController.class).listar()).withRel("todos-los-inventarios"));
    }

    @Operation(summary = "Registrar nuevo stock de producto", description = "Crea un nuevo registro de existencias físicas y límites de alertas para un producto")
    @PostMapping
    public ResponseEntity<EntityModel<Inventario>> registrar(@NonNull @Valid @RequestBody Inventario inventario) 
    {
        Inventario nuevo = inventarioService.guardar(inventario);
        
        EntityModel<Inventario> recurso = EntityModel.of(nuevo,
                linkTo(methodOn(InventarioController.class).obtenerPorId(nuevo.getId())).withSelfRel(),
                linkTo(methodOn(InventarioController.class).obtenerPorProducto(nuevo.getProductoId())).withRel("ver-por-producto"));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
    }

    @Operation(summary = "Descontar stock de un producto", description = "Disminuye la cantidad disponible de un producto tras procesarse un pedido válido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stock descontado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error: Stock insuficiente o producto no registrado en el inventario")
    })
    @PutMapping("/producto/{productoId}/descontar")
    public ResponseEntity<EntityModel<String>> descontar(@NonNull @PathVariable Long productoId, @NonNull @RequestParam Integer cantidad) 
    {
        boolean exito = inventarioService.descontarStock(productoId, cantidad);
        
        if (exito) {
            EntityModel<String> recurso = EntityModel.of("Stock descontado correctamente.",
                    linkTo(methodOn(InventarioController.class).obtenerPorProducto(productoId)).withRel("ver-stock-actualizado"));
            return ResponseEntity.ok(recurso);
        }
        
        EntityModel<String> recursoError = EntityModel.of("Error: Stock insuficiente o producto no registrado en inventario.",
                linkTo(methodOn(InventarioController.class).obtenerPorProducto(productoId)).withRel("ver-stock-disponible"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(recursoError);
    }
}