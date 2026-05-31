package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reportes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reportes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El titulo del reporte es obligatorio")
    private String titulo;

    @NotBlank(message = "El tipo de reporte no puede estar vacio")
    private String tipo;

    @NotNull(message = "La fecha de generacion del reporte es obligatoria")
    private LocalDate fechaGeneracion;

    @NotNull(message = "El monto total calculado es obligatorio")
    private Double montoTotalCalculado;
}