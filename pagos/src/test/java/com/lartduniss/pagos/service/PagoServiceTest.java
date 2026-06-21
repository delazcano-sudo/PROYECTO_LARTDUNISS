package com.lartduniss.pagos.service;

import com.lartduniss.pagos.model.Pago;
import com.lartduniss.pagos.repository.PagoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

   @Mock
   private PagoRepository pagoRepository;

   @Mock
   private RestTemplate restTemplate;

   @InjectMocks
   private PagoService pagoService;

   @Test
   void guardarPagoTotalExitosoTest() {
       // 1. Arrange
       Pago mockPagoInput = new Pago(null, 100L, 20000.0, 20000.0, "Webpay", null, null);
       Pago mockPagoGuardado = new Pago(1L, 100L, 20000.0, 20000.0, "Webpay", LocalDate.now(), "PAGADO_TOTAL");

       Mockito.when(pagoRepository.save(any(Pago.class))).thenReturn(mockPagoGuardado);
       
       Mockito.doNothing().when(restTemplate).put(anyString(), any());

       // 2. Act
       Pago resultado = pagoService.guardar(mockPagoInput);

       // 3. Assert
       assertNotNull(resultado);
       assertEquals("PAGADO_TOTAL", resultado.getEstadoPago());
       Mockito.verify(pagoRepository, Mockito.times(1)).save(mockPagoInput);
   }

   @Test
   void guardarPagoMontoInsuficienteTest() {
       // 1. Arrange
       Pago mockPagoInput = new Pago(null, 101L, 20000.0, 5000.0, "Efectivo", null, null);
       Pago mockPagoGuardado = new Pago(2L, 101L, 20000.0, 5000.0, "Efectivo", LocalDate.now(), "MONTO_INSUFICIENTE");

       Mockito.when(pagoRepository.save(any(Pago.class))).thenReturn(mockPagoGuardado);

       // 2. Act
       Pago resultado = pagoService.guardar(mockPagoInput);

       // 3. Assert
       assertNotNull(resultado);
       assertEquals("MONTO_INSUFICIENTE", resultado.getEstadoPago());
       Mockito.verify(pagoRepository, Mockito.times(1)).save(mockPagoInput);
       Mockito.verify(restTemplate, Mockito.never()).put(anyString(), any());
   }
}