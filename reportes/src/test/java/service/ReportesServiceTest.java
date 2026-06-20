package service;

import model.Reportes;
import repository.ReportesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ReportesServiceTest {

    @Mock
    private ReportesRepository reportesRepository;

    @InjectMocks
    private ReportesService reportesService;

    @Test
    void eliminarReporteExitosoTest() {
        // 1. Arrange <3
        Long reporteId = 1L;
        Reportes reporteMock = new Reportes(reporteId, "Ventas Mayo", "MENSUAL", LocalDate.now(), 540000.0);
        Mockito.when(reportesRepository.findById(reporteId)).thenReturn(Optional.of(reporteMock));

        // 2. Act <3
        boolean resultado = reportesService.eliminarReporte(reporteId);

        // 3. Assert <3
        assertTrue(resultado);
        verify(reportesRepository, times(1)).findById(reporteId);
        verify(reportesRepository, times(1)).delete(reporteMock);
    }

    @Test
    void eliminarReporteInexistenteTest() {
        // 1. Arrange
        Long reporteId = 99L;
        Mockito.when(reportesRepository.findById(reporteId)).thenReturn(Optional.empty());

        // 2. Act
        boolean resultado = reportesService.eliminarReporte(reporteId);

        // 3. Assert
        assertFalse(resultado);
        verify(reportesRepository, times(1)).findById(reporteId);
    }
}