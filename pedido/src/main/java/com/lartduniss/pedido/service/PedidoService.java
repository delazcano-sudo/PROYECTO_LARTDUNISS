package com.lartduniss.pedido.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;
import jakarta.transaction.Transactional;
import com.lartduniss.pedido.model.Pedido;
import com.lartduniss.pedido.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
  
    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
  
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Optional<Pedido> buscarPorId(@NonNull Long id) {
        return pedidoRepository.findById(id);
    }

    @Transactional
    public Pedido crear(@NonNull Pedido pedido) {
        pedido.setFechaCreacion(LocalDate.now());
        pedido.setEstadoPedido("PENDIENTE_PAGO");
        return pedidoRepository.save(pedido);
    }

    @Transactional
    public Pedido actualizarEstado(@NonNull Long id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con el ID: " + id));
      
        pedido.setEstadoPedido(nuevoEstado.replace("\"", "").trim()); // Limpia posibles comillas residuales del string body
        return pedidoRepository.save(pedido);
    }
}