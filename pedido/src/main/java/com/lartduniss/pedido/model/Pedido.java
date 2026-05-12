package com.lartduniss.pedido.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "pedidos")
@Data
public class Pedido 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //La id del cliente
    private Long clienteId;
    private LocalDate fechaCreacion;
    private Double montoTotal;

    private String estadoPedido;

    //Ej; "Con azucar"m "Con dedicatoria"m "sin decoraciones"
    private String observaciones;   

}
