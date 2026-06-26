package com.lartduniss.pagos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.lartduniss.pagos.model.Pago;
import com.lartduniss.pagos.repository.PagoRepository;
import org.springframework.lang.NonNull;
import jakarta.transaction.Transactional;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final RestTemplate restTemplate;

    public PagoService(PagoRepository pagoRepository, RestTemplate restTemplate) {
        this.pagoRepository = pagoRepository;
        this.restTemplate = restTemplate;
    }

    public List<Pago> listarTodos() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> buscarPorId(@NonNull Long id) {
        return pagoRepository.findById(id);
    }

    @Transactional
    public Pago guardar(@NonNull Pago pago) {
        double abonoMinimo = pago.getMontoTotal() * 0.5;
      
        if (pago.getMontoPagado() >= pago.getMontoTotal()) {
            pago.setEstadoPago("PAGADO_TOTAL");
        } else if (pago.getMontoPagado() >= abonoMinimo) {
            pago.setEstadoPago("ABONO_CONFIRMADO");
        } else {
            pago.setEstadoPago("MONTO_INSUFICIENTE");
        }
      
        pago.setFechaPago(LocalDate.now());
        Pago pagoGuardado = pagoRepository.save(pago);
      
        if (!pagoGuardado.getEstadoPago().equals("MONTO_INSUFICIENTE")) {
            try {
                String urlPedido = "http://localhost:9090/pedidos/" + pagoGuardado.getPedidoId() + "/estado";
                String nuevoEstadoPedido = pagoGuardado.getEstadoPago();
                restTemplate.put(urlPedido, nuevoEstadoPedido);
            } catch (Exception e) {
                System.err.println("Error al notificar al microservicio de Pedidos: " + e.getMessage());
            }
        }
      
        return pagoGuardado;
    }

    @Transactional
    public void eliminar(@NonNull Long id) {
        pagoRepository.deleteById(id);
    }
}