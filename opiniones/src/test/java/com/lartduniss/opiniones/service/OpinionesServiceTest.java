package com.lartduniss.opiniones.service;

import com.lartduniss.opiniones.model.Opiniones;
import com.lartduniss.opiniones.repository.OpinionesRepository;
import com.lartduniss.opiniones.service.OpinionesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OpinionesServiceTest {

    @Mock
    private OpinionesRepository opinionesRepository;

    @InjectMocks
    private OpinionesService opinionesService;

    @Test
    void obtenerPromedioCalificacionTest() {
        // 1. Arrange <3
        Long productoId = 200L;
        Opiniones op1 = new Opiniones(1L, productoId, 1L, 5, "Excelente producto", LocalDate.now());
        Opiniones op2 = new Opiniones(2L, productoId, 2L, 3, "Regular", LocalDate.now());
        List<Opiniones> listaMock = Arrays.asList(op1, op2);

        Mockito.when(opinionesRepository.findByProductoId(productoId)).thenReturn(listaMock);

        // 2. Act <3
        Double promedio = opinionesService.obtenerPromedioCalificacion(productoId);

        // 3. Assert <3
        assertNotNull(promedio);
        assertEquals(4.0, promedio); // (5 + 3) / 2 = 4.0
        Mockito.verify(opinionesRepository, Mockito.times(1)).findByProductoId(productoId);
    }

    @Test
    void obtenerPromedioSinOpinionesTest() {
        // 1. Arrange
        Long productoId = 200L;
        Mockito.when(opinionesRepository.findByProductoId(productoId)).thenReturn(Collections.emptyList());

        // 2. Act
        Double promedio = opinionesService.obtenerPromedioCalificacion(productoId);

        // 3. Assert
        assertEquals(0.0, promedio);
        Mockito.verify(opinionesRepository, Mockito.times(1)).findByProductoId(productoId);
    }
}