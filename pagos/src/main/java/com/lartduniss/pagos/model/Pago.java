package com.lartduniss.pagos.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull(message = "El id del pedido es obligatorio")
    private Long pedidoId;

    @NotNull(message = "El monto total es obligatorio")
    @Positive(message = "El monto total deebe ser un valor positivo")
    private Double montoTotal;

    @NotNull(message = "El monto pagado no puede ser nulo")
    @Positive(message = "El monto pagado debe ser un valor positivo")
    private Double montoPagado;

    
    private String metodoPago;
    private LocalDate fechaPago;
    private String estadoPago;

}
