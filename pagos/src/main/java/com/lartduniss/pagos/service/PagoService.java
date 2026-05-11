package com.lartduniss.pagos.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lartduniss.pagos.model.Pago;
import com.lartduniss.pagos.repository.PagoRepository;

import jakarta.transaction.Transactional;

@Service
public class PagoService 
{
    @Autowired
    private PagoRepository pagoRepository;
    
    public List<Pago> listarTodos() {
        return pagoRepository.findAll();
    }

    public Optional<Pago> buscarPorId(Long id){
        return pagoRepository.findById(id);
    }

    @Transactional
    public Pago guardar (Pago pago)
    {
        //Calcular el 50% del total
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
        return pagoRepository.save(pago);
    }

    public void eliminar(Long id)
    {
        pagoRepository.deleteById(id);
    }

}
