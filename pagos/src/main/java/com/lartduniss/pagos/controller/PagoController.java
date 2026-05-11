package com.lartduniss.pagos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lartduniss.pagos.model.Pago;
import com.lartduniss.pagos.service.PagoService;

@RestController
@RequestMapping("/pagos")
public class PagoController 
{
    @Autowired
    private PagoService pagoService;
    
    @GetMapping
    public List<Pago> listar()
    {
        return pagoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtener(@PathVariable Long id)
    {
        return pagoService.buscarPorId(id).
        map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Pago> crear(@RequestBody Pago pago)
    {
        return ResponseEntity.ok(pagoService.guardar(pago));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id)
    {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
