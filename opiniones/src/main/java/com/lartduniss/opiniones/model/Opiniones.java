package com.lartduniss.opiniones.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema; // Importante añadir para documentar

@Entity
@Table(name = "opiniones")
@Data
@Schema(description = "Modelo que representa la opinión o reseña de un cliente sobre un producto")
@AllArgsConstructor
@NoArgsConstructor
public class Opiniones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental de la reseña", example = "1")
    private Long id;

    @NotNull(message = "El ID del producto es obligatorio")
    @Schema(description = "ID del producto (Cupcake) asociado a la opinión", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productoId;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Schema(description = "ID del cliente que redactó la opinión", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long clienteId;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = 1, message = "La calificación mínima es 1 estrella")
    @Max(value = 5, message = "La calificación máxima es 5 estrellas")
    @Schema(description = "Calificación otorgada de 1 a 5 estrellas", example = "5", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer calificacion;

    @NotBlank(message = "El comentario no puede estar vacío")
    @Size(max = 500, message = "El comentario no puede superar los 500 caracteres")
    @Schema(description = "Texto descriptivo de la opinión del usuario", example = "¡El cupcake de red velvet estaba exquisito y súper esponjoso!", requiredMode = Schema.RequiredMode.REQUIRED)
    private String comentario;

    @Schema(description = "Fecha en la que se registró la opinión en el sistema", example = "2026-06-19")
    private LocalDate fechaPublicacion;
}