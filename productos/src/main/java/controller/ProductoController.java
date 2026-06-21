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
import org.springframework.web.bind.annotation.*;

import model.Producto;
import service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos", description = "Controlador para la gestión del catálogo de productos de Lartduniss")
@SuppressWarnings("null")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Obtener todos los productos", description = "Retorna el catálogo completo con enlaces hipermedia HATEOAS")
    @GetMapping
    public CollectionModel<EntityModel<Producto>> listar() {
        List<EntityModel<Producto>> productos = productoService.obtenerTodos().stream()
                .map(producto -> EntityModel.of(producto,
                        linkTo(methodOn(ProductoController.class).obtenerUno(producto.getId())).withSelfRel(),
                        linkTo(methodOn(ProductoController.class).listar()).withRel("productos")))
                .collect(Collectors.toList());
        
        return CollectionModel.of(productos,
                linkTo(methodOn(ProductoController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Guardar un nuevo producto", description = "Registra un producto en el catálogo verificando reglas de precio y stock. Acción exclusiva de ADMINISTRADORES.")
    @PostMapping
    public ResponseEntity<EntityModel<Producto>> crear(@NonNull @Valid @RequestBody Producto producto) {
        Producto nuevo = productoService.guardar(producto);
        EntityModel<Producto> recurso = EntityModel.of(nuevo,
                linkTo(methodOn(ProductoController.class).obtenerUno(nuevo.getId())).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listar()).withRel("todos-los-productos"));
        return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
    }

    @Operation(summary = "Buscar un producto por ID", description = "Retorna un producto específico con sus enlaces de navegación")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado con éxito"),
        @ApiResponse(responseCode = "404", description = "El ID del producto solicitado no existe")
    })
    @GetMapping("/{id}")
    public EntityModel<Producto> obtenerUno(@NonNull @PathVariable Long id) {
        Producto producto = productoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));
        
        return EntityModel.of(producto,
                linkTo(methodOn(ProductoController.class).obtenerUno(id)).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listar()).withRel("todos-los-productos"),
                linkTo(methodOn(ProductoController.class).actualizar(id, new Producto())).withRel("actualizar-producto"));
    }

    @Operation(summary = "Actualizar un producto existente", description = "Modifica los datos de un producto en el catálogo. Acción exclusiva de ADMINISTRADORES.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "El producto a actualizar no existe")
    })
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> actualizar(@NonNull @PathVariable Long id, @NonNull @Valid @RequestBody Producto productoDatos) {
        Producto productoActualizado = productoService.buscarPorId(id)
                .map(productoExistente -> {
                    productoExistente.setNombre(productoDatos.getNombre());
                    productoExistente.setPrecio(productoDatos.getPrecio());
                    productoExistente.setStock(productoDatos.getStock());
                    return productoService.guardar(productoExistente);
                })
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con el ID: " + id));

        EntityModel<Producto> recurso = EntityModel.of(productoActualizado,
                linkTo(methodOn(ProductoController.class).obtenerUno(id)).withSelfRel(),
                linkTo(methodOn(ProductoController.class).listar()).withRel("todos-los-productos"));
        
        return ResponseEntity.ok(recurso);
    }
}