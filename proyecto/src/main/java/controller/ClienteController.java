package controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import model.Cliente;
import service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Controlador para la gestión y registro de perfiles de clientes")
@SuppressWarnings("null")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listar todos los clientes", description = "Retorna el listado de clientes registrados con soporte hipermedia HATEOAS")
    @GetMapping
    public CollectionModel<EntityModel<Cliente>> listar() {
        List<EntityModel<Cliente>> clientes = clienteService.obtenerTodos().stream()
                .map(cliente -> EntityModel.of(cliente,
                        linkTo(methodOn(ClienteController.class).obtenerUno(cliente.getId())).withSelfRel(),
                        linkTo(methodOn(ClienteController.class).listar()).withRel("clientes")))
                .collect(Collectors.toList());
        
        return CollectionModel.of(clientes,
                linkTo(methodOn(ClienteController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Registrar un nuevo cliente", description = "Crea un registro de cliente validando que el email sea único y posea formato correcto")
    @PostMapping
    public ResponseEntity<EntityModel<Cliente>> crear(@NonNull @Valid @RequestBody Cliente cliente) {
        Cliente nuevo = clienteService.guardarCliente(cliente);
        EntityModel<Cliente> recurso = EntityModel.of(nuevo,
                linkTo(methodOn(ClienteController.class).obtenerUno(nuevo.getId())).withSelfRel(),
                linkTo(methodOn(ClienteController.class).listar()).withRel("todos-los-clientes"));
        return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
    }

    @Operation(summary = "Obtener un cliente por ID", description = "Busca un cliente específico en el sistema según su identificador único")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente localizado con éxito"),
        @ApiResponse(responseCode = "404", description = "El ID del cliente no existe en los registros")
    })
    @GetMapping("/{id}")
    public EntityModel<Cliente> obtenerUno(@NonNull @PathVariable Long id) {
        Cliente cliente = clienteService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con el ID: " + id));
        
        return EntityModel.of(cliente,
                linkTo(methodOn(ClienteController.class).obtenerUno(id)).withSelfRel(),
                linkTo(methodOn(ClienteController.class).listar()).withRel("todos-los-clientes"),
                linkTo(methodOn(ClienteController.class).actualizar(id, null)).withRel("actualizar-cliente"));
    }

    @Operation(summary = "Actualizar datos de un cliente", description = "Permite modificar el nombre, email o teléfono de un cliente existente")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Cliente>> actualizar(@NonNull @PathVariable Long id, @NonNull @Valid @RequestBody Cliente clienteDatos) {
        Cliente clienteActualizado = clienteService.buscarPorId(id)
                .map(clienteExistente -> {
                    clienteExistente.setNombre(clienteDatos.getNombre());
                    clienteExistente.setEmail(clienteDatos.getEmail());
                    clienteExistente.setTelefono(clienteDatos.getTelefono());
                    return clienteService.guardarCliente(clienteExistente);
                })
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con el ID: " + id));

        EntityModel<Cliente> recurso = EntityModel.of(clienteActualizado,
                linkTo(methodOn(ClienteController.class).obtenerUno(id)).withSelfRel(),
                linkTo(methodOn(ClienteController.class).listar()).withRel("todos-los-clientes"));
        
        return ResponseEntity.ok(recurso);
    }
}