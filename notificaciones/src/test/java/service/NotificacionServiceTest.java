package service;

import model.Notificacion;
import repository.NotificacionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    @Test
    void guardarNotificacionAsignaFechaActualTest() {
        // 1. Arrange 
        Notificacion notificacionMock = new Notificacion(null, "denisse@test.com", "Email", "Tu orden está lista", null);
        Notificacion notificacionGuardada = new Notificacion(1L, "denisse@test.com", "Email", "Tu orden está lista", LocalDateTime.now());
        
        when(notificacionRepository.save(any(Notificacion.class))).thenReturn(notificacionGuardada);

        // 2. Act 
        Notificacion resultado = notificacionService.guardarNotificacion(notificacionMock);

        // 3. Assert 
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertNotNull(notificacionMock.getFechaEnvio()); // Comprobamos que el servicio le asignó la fecha actual antes de guardar
        verify(notificacionRepository, times(1)).save(notificacionMock);
    }

    @Test
    void buscarPorIdTest() {
        // 1. Arrange
        Long id = 5L;
        Notificacion notificacionExistente = new Notificacion(id, "cliente@test.com", "WhatsApp", "Su pago fue recibido", LocalDateTime.now());
        
        when(notificacionRepository.findById(id)).thenReturn(Optional.of(notificacionExistente));

        // 2. Act
        Optional<Notificacion> resultado = notificacionService.buscarPorId(id);

        // 3. Assert
        assertTrue(resultado.isPresent());
        assertEquals("WhatsApp", resultado.get().getTipo());
        verify(notificacionRepository, times(1)).findById(id);
    }
}