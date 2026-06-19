package com.lartduniss.pagos.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Schema(description = "Represeta el pago de un pedido")
@AllArgsConstructor
@NoArgsConstructor
public class Pago 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del registro de pago", example = "1")
    private Long id;

    @NotNull(message = "El id del pedido es obligatorio")
    @Positive(message = "El id del pedido debe ser un valor positivo")
    @Schema(description = "ID del pedido proveniente del microservicio de Pedidos", example = "10000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long pedidoId; 

    @NotNull(message = "El monto total es obligatorio")
    @Positive(message = "El monto total debe ser un valor positivo")
    @Schema(description = "Monto total a pagar por el pedido", example = "15500.00")
    private Double montoTotal;

    @NotNull(message = "El monto pagado no puede ser nulo")
    @Positive(message = "El monto pagado debe ser un valor positivo")
    @Schema(description = "Monto que el cliente ya ha cancelado", example = "15500.00")
    private Double montoPagado;

    @NotBlank(message = "El método de pago es obligatorio")
    @Schema(description = "Método utilizado para el pago", example = "Webpay", allowableValues = {"Webpay", "Transferencia", "Efectivo"})
    private String metodoPago;

    @NotNull(message = "La fecha de pago es obligatoria")
    @Schema(description = "Fecha en la que se procesó el pago", example = "2026-06-16")
    private LocalDate fechaPago;

    @NotBlank(message = "El estado del pago es obligatorio")
    @Schema(description = "Estado actual de la transacción", example = "APROBADO")
    private String estadoPago;
}
