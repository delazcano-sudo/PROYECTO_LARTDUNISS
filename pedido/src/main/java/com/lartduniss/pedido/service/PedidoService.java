package com.lartduniss.pedido.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull; 
import jakarta.transaction.Transactional; 

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

    // Agregamos @NonNull como en el buscarPorId de la profe
    public Optional<Pedido> buscarPorId(@NonNull Long id)
    {
        return pedidoRepository.findById(id);
    }

    @Transactional // Buena práctica para operaciones de guardado
    public Pedido crear(Pedido pedido)
    {
        pedido.setFechaCreacion(LocalDate.now());
        pedido.setEstadoPedido("PENDIENTE_PAGO");
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido actualizarEstado(@NonNull Long id, String nuevoEstado) 
    {
        // En vez de orElse(null), usamos orElseThrow para encajar con la gestión de errores global
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con el ID: " + id));
        
        pedido.setEstadoPedido(nuevoEstado);
        return pedidoRepository.save(pedido);
    }
}