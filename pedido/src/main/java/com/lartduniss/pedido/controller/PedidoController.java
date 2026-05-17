package com.lartduniss.pedido.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lartduniss.pedido.model.Pedido;
import com.lartduniss.pedido.service.PedidoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
public class PedidoController 
{
    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public List<Pedido> listar() 
    {
        return pedidoService.listarTodos();
    }

    @PostMapping
    //Al crear un pedido el service le pondra "Pendiente_Pago" de forma automatica
    public ResponseEntity<Pedido> crear(@Valid @RequestBody Pedido pedido)
    {
        Pedido nuevoPedido = pedidoService.crear(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) 
    {
        return pedidoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable long id, @RequestBody String nuevoEstado)
    {
        Pedido actualizado = pedidoService.actualizarEstado(id, nuevoEstado);
        if(actualizado != null)
            {
                return ResponseEntity.ok(actualizado);
            }
        return ResponseEntity.notFound().build();
    }

}
