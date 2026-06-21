package com.lartduniss.inventario.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.lartduniss.inventario.model.Inventario;
import com.lartduniss.inventario.service.InventarioService;

// ANOTACIONES SWAGGER <3 
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Controlador de Inventario", description = "Endpoints para la gestión, control de stock y alertas de productos")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary = "Listar todo el inventario", description = "Retorna una lista completa de las existencias de todos los productos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de inventario obtenida con éxito")
    public List<Inventario> listar() {
        return inventarioService.listarTodo();
    }

    @GetMapping("/producto/{productoId}")
    @Operation(summary = "Obtener inventario por ID de Producto", description = "Busca el registro de stock asociado a un producto específico")
    @ApiResponse(responseCode = "200", description = "Registro de inventario encontrado")
    @ApiResponse(responseCode = "404", description = "Producto no registrado en el inventario",
                 content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = exception.ErrorResponse.class)))
    public ResponseEntity<Inventario> obtenerPorProducto(@PathVariable Long productoId) {
        Inventario inventario = inventarioService.buscarPorProducto(productoId)
                .orElseThrow(() -> new RuntimeException("El producto con ID " + productoId + " no se encuentra registrado en el inventario."));
        return new ResponseEntity<>(inventario, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Registrar stock inicial", description = "Crea un nuevo registro de inventario y establece alertas de stock mínimo")
    @ApiResponse(responseCode = "201", description = "Inventario registrado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o negativos")
    public ResponseEntity<Inventario> registrar(@Valid @RequestBody Inventario inventario) {
        Inventario nuevo = inventarioService.guardar(inventario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/producto/{productoId}/descontar")
    @Operation(summary = "Descontar stock disponible", description = "Disminuye la cantidad de unidades en base a una venta o pedido")
    @ApiResponse(responseCode = "200", description = "Stock descontado correctamente")
    @ApiResponse(responseCode = "400", description = "Stock insuficiente o producto no registrado")
    public ResponseEntity<String> descontar(@PathVariable Long productoId, @RequestParam Integer cantidad) {
        boolean exito = inventarioService.descontarStock(productoId, cantidad);
        if (exito) {
            return ResponseEntity.ok("Stock descontado correctamente.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error: Stock insuficiente o producto no registrado en inventario.");
    }
}