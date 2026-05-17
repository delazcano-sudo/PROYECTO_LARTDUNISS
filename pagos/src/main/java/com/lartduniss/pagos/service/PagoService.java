package com.lartduniss.pagos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate; // ¡IMPORTANTE: Agrega esta importación!

import com.lartduniss.pagos.model.Pago;
import com.lartduniss.pagos.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
public class PagoService 
{
    @Autowired
    private PagoRepository pagoRepository;
    
    @Autowired
    private RestTemplate restTemplate;

    public List<Pago> listarTodos() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> buscarPorId(Long id){
        return pagoRepository.findById(id);
    }

    @Transactional
    public Pago guardar(Pago pago)
    {
        double abonoMinimo = pago.getMontoTotal() * 0.5;
        
        if (pago.getMontoPagado() >= pago.getMontoTotal())
        {
            pago.setEstadoPago("PAGADO_TOTAL");
        }
        else if (pago.getMontoPagado() >= abonoMinimo)
        {
            pago.setEstadoPago("ABONO_CONFIRMADO");
        }
        else 
        {
            pago.setEstadoPago("MONTO_INSUFICIENTE");
        }
        
        pago.setFechaPago(LocalDate.now());
        
        Pago pagoGuardado = pagoRepository.save(pago);
        
        if (!pagoGuardado.getEstadoPago().equals("MONTO_INSUFICIENTE")) {
            try {
                String urlPedido = "http://localhost:8080/pedidos/" + pagoGuardado.getPedidoId() + "/estado";
                String nuevoEstadoPedido = pagoGuardado.getEstadoPago(); 
                
                restTemplate.put(urlPedido, nuevoEstadoPedido);
            } catch (Exception e) {
                System.out.println("Error al notificar al microservicio de Pedidos: " + e.getMessage());
            }
        }
        
        return pagoGuardado;
    }

    public void eliminar(Long id)
    {
        pagoRepository.deleteById(id);
    }
}
