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
@Table(name = "despachos") 
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Despacho { 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La dirección de entrega es obligatoria")
    private String direccionEntrega;

    @NotBlank(message = "El estado del despacho es obligatorio")
    private String estado;

    @NotNull(message = "La fecha programada es obligatoria")
    private LocalDate fechaProgramada;

    @NotNull(message = "El costo de envío es obligatorio")
    private Double costoEnvio;
}