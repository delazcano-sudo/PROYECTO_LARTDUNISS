package com.lartduniss.inventario.service;

import com.lartduniss.inventario.model.Inventario;
import com.lartduniss.inventario.repository.InventarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void descontarStockExitosoTest() {
        // 1. Arrange
        Long productoId = 101L;
        Integer cantidadADescontar = 3;
        Inventario inventarioMock = new Inventario(1L, productoId, 10, 5);

        when(inventarioRepository.findByProductoId(productoId)).thenReturn(Optional.of(inventarioMock));
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioMock);

        // 2. Act
        boolean resultado = inventarioService.descontarStock(productoId, cantidadADescontar);

        // 3. Assert
        assertTrue(resultado);
        assertEquals(7, inventarioMock.getCantidadDisponible()); // 10 - 3 = 7
        verify(inventarioRepository, times(1)).findByProductoId(productoId);
        verify(inventarioRepository, times(1)).save(inventarioMock);
    }

    @Test
    void descontarStockInsuficienteTest() {
        // 1. Arrange
        Long productoId = 101L;
        Integer cantidadADescontar = 15; // Es mayor que el stock disponible
        Inventario inventarioMock = new Inventario(1L, productoId, 10, 5);

        when(inventarioRepository.findByProductoId(productoId)).thenReturn(Optional.of(inventarioMock));

        // 2. Act
        boolean resultado = inventarioService.descontarStock(productoId, cantidadADescontar);

        // 3. Assert
        assertFalse(resultado);
        assertEquals(10, inventarioMock.getCantidadDisponible()); // No se debe haber descontado nada
        verify(inventarioRepository, times(1)).findByProductoId(productoId);
        verify(inventarioRepository, never()).save(any(Inventario.class));
    }
}