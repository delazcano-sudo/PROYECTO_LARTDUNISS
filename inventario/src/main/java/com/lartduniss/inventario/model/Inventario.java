package com.lartduniss.inventario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema; 

@Entity
@Table(name = "inventario")
@Data
@Schema(description = "Modelo que representa el control de existencias y alertas de stock de un producto en el sistema")
@AllArgsConstructor
@NoArgsConstructor
public class Inventario 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del registro de inventario", example = "1")
    private Long id;

    @NotNull(message = "El ID del producto es obligatorio")
    @Schema(description = "ID del producto (Cupcake) al que se le controla el stock", example = "15", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productoId;

    @NotNull(message = "La cantidad disponible es obligatoria")
    @Min(value = 0, message = "La cantidad en stock no puede ser negativa")
    @Schema(description = "Cantidad física de unidades actualmente disponibles para la venta", example = "50", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer cantidadDisponible;

    @NotNull(message = "El stock mínimo de alerta es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    @Schema(description = "Límite inferior de unidades permitidas antes de gatillar una alerta de reabastecimiento", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer stockMinimoAlerta; 
    // Ej: Si baja de 10, el sistema debería avisar que queda poco
}