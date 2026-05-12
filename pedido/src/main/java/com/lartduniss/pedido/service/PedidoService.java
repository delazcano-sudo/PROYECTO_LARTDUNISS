package com.lartduniss.pedido.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lartduniss.pedido.model.Pedido;
import com.lartduniss.pedido.repository.PedidoRepository;

@Service
public class PedidoService 
{
    @Autowired
    private PedidoRepository pedidoRepository;
    
    public List<Pedido> listarTodos() 
    {
        return pedidoRepository.findAll();
    }

    public Pedido crear(Pedido pedido)
    {
        pedido.setFechaCreacion(LocalDate.now());
        pedido.setEstadoPedido("PENDIENTE_PAGO");
        return pedidoRepository.save(pedido);
    }

    public Pedido actualizarEstado(Long id, String nuevoEstado) 
    {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido != null)
            {
                pedido.setEstadoPedido(nuevoEstado);
                return pedidoRepository.save(pedido);
            }
        return null;
    }

}
