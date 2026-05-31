package com.lartduniss.inventario.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.lartduniss.inventario.model.Inventario;
import com.lartduniss.inventario.service.InventarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController 
{
    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public List<Inventario> listar() {
        return inventarioService.listarTodo();
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<Inventario> obtenerPorProducto(@PathVariable Long productoId) {
        return inventarioService.buscarPorProducto(productoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Inventario> registrar(@Valid @RequestBody Inventario inventario) {
        Inventario nuevo = inventarioService.guardar(inventario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // Endpoint clave para que lo usen otros módulos
    @PutMapping("/producto/{productoId}/descontar")
    public ResponseEntity<String> descontar(@PathVariable Long productoId, @RequestParam Integer cantidad) {
        boolean exito = inventarioService.descontarStock(productoId, cantidad);
        if (exito) {
            return ResponseEntity.ok("Stock descontado correctamente.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error: Stock insuficiente o producto no registrado en inventario.");
    }
}