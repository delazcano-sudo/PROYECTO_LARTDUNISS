package com.lartduniss.pedido.service;

import com.lartduniss.pedido.model.Pedido;
import com.lartduniss.pedido.repository.PedidoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void crearPedidoInicializaEstadoTest() {
        // 1. Arrange
        Pedido pedidoInput = new Pedido(null, 55L, null, 15500.0, "PENDIENTE", "Notas");
        Pedido pedidoGuardado = new Pedido(10000L, 55L, LocalDate.now(), 15500.0, "PENDIENTE_PAGO", "Notas");

        Mockito.when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoGuardado);

        // 2. Act
        Pedido resultado = pedidoService.crear(pedidoInput);

        // 3. Assert 
        assertNotNull(resultado);
        assertEquals("PENDIENTE_PAGO", resultado.getEstadoPedido());
        Mockito.verify(pedidoRepository, Mockito.times(1)).save(pedidoInput);
    }

    @Test
    void actualizarEstadoPedidoExitosoTest() {
        // 1. Arrange
        Long pedidoId = 10000L;
        Pedido pedidoExistente = new Pedido(pedidoId, 55L, LocalDate.now(), 15500.0, "PENDIENTE_PAGO", "Notas");
        Pedido pedidoActualizado = new Pedido(pedidoId, 55L, LocalDate.now(), 15500.0, "PAGADO_TOTAL", "Notas");

        Mockito.when(pedidoRepository.findById(pedidoId)).thenReturn(Optional.of(pedidoExistente));
        Mockito.when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoActualizado);

        // 2. Act
        Pedido resultado = pedidoService.actualizarEstado(pedidoId, "PAGADO_TOTAL");

        // 3. Assert
        assertNotNull(resultado);
        assertEquals("PAGADO_TOTAL", resultado.getEstadoPedido());
        Mockito.verify(pedidoRepository, Mockito.times(1)).findById(pedidoId);
        Mockito.verify(pedidoRepository, Mockito.times(1)).save(pedidoExistente);
    }
}