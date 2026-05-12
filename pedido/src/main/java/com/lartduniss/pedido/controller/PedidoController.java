package com.lartduniss.pedido.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido)
    {
        return ResponseEntity.ok(pedidoService.crear(pedido));
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
