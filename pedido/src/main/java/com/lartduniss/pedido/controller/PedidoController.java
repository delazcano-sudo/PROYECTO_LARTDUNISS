package com.lartduniss.pedido.controller;

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

import com.lartduniss.pedido.model.Pedido;
import com.lartduniss.pedido.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/pedidos")
@Tag(name = "Pedidos", description = "Operaciones relacionadas con la gestión de pedidos con soporte HATEOAS")
@SuppressWarnings("null")
public class PedidoController 
{
    @Autowired
    private PedidoService pedidoService;

    @Operation(summary = "Obtener todos los pedidos", description = "Retorna una colección de pedidos con sus respectivos enlaces hipermedia")
    @GetMapping
    public CollectionModel<EntityModel<Pedido>> listar() 
    {
        List<EntityModel<Pedido>> pedidos = pedidoService.listarTodos().stream()
                .map(pedido -> EntityModel.of(pedido,
                        linkTo(methodOn(PedidoController.class).obtenerPorId(pedido.getId())).withSelfRel(),
                        linkTo(methodOn(PedidoController.class).listar()).withRel("pedidos")))
                .collect(Collectors.toList());

        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Crear un nuevo pedido", description = "Registra el pedido e inicializa su estado como PENDIENTE_PAGO de forma automática")
    @PostMapping
    public ResponseEntity<EntityModel<Pedido>> crear(@NonNull @Valid @RequestBody Pedido pedido)
    {
        Pedido nuevoPedido = pedidoService.crear(pedido);
        
        EntityModel<Pedido> recurso = EntityModel.of(nuevoPedido,
                linkTo(methodOn(PedidoController.class).obtenerPorId(nuevoPedido.getId())).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listar()).withRel("todos-los-pedidos"));
                
        return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
    }

    @Operation(summary = "Obtener pedido por ID", description = "Retorna un pedido individual con enlaces a acciones relacionadas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "El pedido no existe")
    })
    @GetMapping("/{id}")
    public EntityModel<Pedido> obtenerPorId(@NonNull @PathVariable Long id) 
    {
        
        Pedido pedido = pedidoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        return EntityModel.of(pedido,
                linkTo(methodOn(PedidoController.class).obtenerPorId(id)).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listar()).withRel("todos-los-pedidos"),
                linkTo(methodOn(PedidoController.class).actualizarEstado(id, null)).withRel("actualizar-estado"));
    }

    @Operation(summary = "Actualizar el estado de un pedido", description = "Endpoint utilizado principalmente por el microservicio de Pagos para cambiar el estado de la transacción")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado del pedido actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "El pedido a actualizar no existe")
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<EntityModel<Pedido>> actualizarEstado(@NonNull @PathVariable Long id, @RequestBody String nuevoEstado)
    {
        Pedido actualizado = pedidoService.actualizarEstado(id, nuevoEstado);
        
        EntityModel<Pedido> recurso = EntityModel.of(actualizado,
                linkTo(methodOn(PedidoController.class).obtenerPorId(id)).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listar()).withRel("todos-los-pedidos"));
                
        return ResponseEntity.ok(recurso);
    }
}