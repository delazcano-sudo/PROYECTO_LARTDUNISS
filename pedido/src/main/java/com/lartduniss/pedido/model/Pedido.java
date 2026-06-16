package com.lartduniss.pedido.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // <-- Importación corregida para números
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "pedidos")
@Data
@Schema(description = "Modelo que representa un pedido dentro del sistema de ventas")
@AllArgsConstructor
@NoArgsConstructor
public class Pedido 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del pedido", example = "10000")
    private Long id;

    @NotNull(message = "El id del cliente no puede estar vacío")
    @Positive(message = "El id del cliente debe ser un valor positivo")
    @Schema(description = "ID del cliente proveniente del microservicio de Clientes", example = "55", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long clienteId; // Al ser microservicios, manejamos la llave lógica igual que con el pago

    @Schema(description = "Fecha en la que se registró el pedido en el sistema", example = "2026-06-16")
    private LocalDate fechaCreacion;

    @NotNull(message = "El monto total es obligatorio")
    @Positive(message = "El monto total debe ser un valor positivo")
    @Schema(description = "Monto total calculado para el pedido", example = "15500.00", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double montoTotal;

    @NotBlank(message = "El estado del pedido es obligatorio")
    @Schema(description = "Estado actual del ciclo del pedido", example = "PENDIENTE", allowableValues = {"PENDIENTE", "ABONO_CONFIRMADO", "PAGADO_TOTAL", "CANCELADO"})
    private String estadoPedido;

    @Schema(description = "Notas adicionales o instrucciones especiales para el pedido", example = "Entregar después de las 18:00 hrs")
    private String observaciones;   
}