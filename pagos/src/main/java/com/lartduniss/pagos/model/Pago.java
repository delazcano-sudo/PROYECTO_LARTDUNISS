package com.lartduniss.pagos.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "pagos")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Pago 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long pedidoId;
    private Double montoTotal;
    private Double montoPagado;
    private String metodoPago;
    private LocalDate fechaPago;
    private String estadoPago;

}
