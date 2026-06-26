package service;

import model.Despacho;
import repository.DespachoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DespachoServiceTest {

    @Mock
    private DespachoRepository despachoRepository;

    @InjectMocks
    private DespachoService despachoService;

    @Test
    void guardarDespachoTest() {
        // 1. Arrange 
        Despacho despachoMock = new Despacho(null, "Av. Vitacura 1234", "PENDIENTE", LocalDate.now(), 4500.0);
        Despacho despachoGuardado = new Despacho(1L, "Av. Vitacura 1234", "PENDIENTE", LocalDate.now(), 4500.0);

        when(despachoRepository.save(despachoMock)).thenReturn(despachoGuardado);

        // 2. Act
        Despacho resultado = despachoService.guardarDespacho(despachoMock);

        // 3. Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Av. Vitacura 1234", resultado.getDireccionEntrega());
        verify(despachoRepository, times(1)).save(despachoMock);
    }

    @Test
    void buscarPorIdTest() {
        // 1. Arrange
        Long id = 1L;
        Despacho despachoGuardado = new Despacho(id, "Av. Vitacura 1234", "PENDIENTE", LocalDate.now(), 4500.0);

        when(despachoRepository.findById(id)).thenReturn(Optional.of(despachoGuardado));

        // 2. Act
        Optional<Despacho> resultado = despachoService.buscarPorId(id);

        // 3. Assert
        assertTrue(resultado.isPresent());
        assertEquals("PENDIENTE", resultado.get().getEstado());
        verify(despachoRepository, times(1)).findById(id);
    }
}