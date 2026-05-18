package com.lartduniss.pedido.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull; // <-- Importación corregida para números
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Table(name = "pedidos")
@Data
public class Pedido 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El id del cliente no puede estar vacio")
    private Long clienteId;

    private LocalDate fechaCreacion;

    // Corregido: Cambie el @NotBlank por @NotNull porque es un Double numérico y el @NotBlank es para cadenas de String no para numeros
    @NotNull(message = "El monto total es obligatorio")
    @Positive(message = "El monto total debe ser un valor positivo")
    private Double montoTotal;
    private String estadoPedido;
    private String observaciones;   

}