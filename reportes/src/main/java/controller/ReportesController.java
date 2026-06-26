package controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import model.Reportes;
import service.ReportesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reportes")
@Tag(name = "Reportes", description = "Controlador para la generación y auditoría de reportes financieros")
@SuppressWarnings("null")
public class ReportesController {

    private final ReportesService reportesService;

    public ReportesController(ReportesService reportesService) {
        this.reportesService = reportesService;
    }

    @Operation(summary = "Obtener todos los reportes", description = "Retorna el histórico completo de balances consolidados. Exclusivo de ADMINISTRADORES.")
    @GetMapping
    public CollectionModel<EntityModel<Reportes>> listar() {
        List<EntityModel<Reportes>> reportes = reportesService.obtenerTodos().stream()
                .map(reporte -> EntityModel.of(reporte,
                        linkTo(methodOn(ReportesController.class).obtenerUno(reporte.getId())).withSelfRel(),
                        linkTo(methodOn(ReportesController.class).listar()).withRel("reportes")))
                .collect(Collectors.toList());
        
        return CollectionModel.of(reportes,
                linkTo(methodOn(ReportesController.class).listar()).withSelfRel());
    }

    @Operation(summary = "Crear un nuevo reporte", description = "Registra un nuevo balance financiero en el sistema")
    @PostMapping
    public ResponseEntity<EntityModel<Reportes>> crear(@Valid @RequestBody Reportes reporte) {
        Reportes nuevo = reportesService.guardarReporte(reporte);
        EntityModel<Reportes> recurso = EntityModel.of(nuevo,
                linkTo(methodOn(ReportesController.class).obtenerUno(nuevo.getId())).withSelfRel(),
                linkTo(methodOn(ReportesController.class).listar()).withRel("todos-los-reportes"));
        return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
    }

    @Operation(summary = "Buscar reporte por ID", description = "Obtiene los detalles de auditoría de un reporte específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte localizado correctamente"),
        @ApiResponse(responseCode = "404", description = "El ID del reporte no existe")
    })
    @GetMapping("/{id}")
    public EntityModel<Reportes> obtenerUno(@PathVariable Long id) {
        Reportes reporte = reportesService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Reporte no encontrado con el ID: " + id));
        
        return EntityModel.of(reporte,
                linkTo(methodOn(ReportesController.class).obtenerUno(id)).withSelfRel(),
                linkTo(methodOn(ReportesController.class).listar()).withRel("todos-los-reportes"));
    }

    @Operation(summary = "Modificar un reporte existente", description = "Permite corregir títulos, tipos o montos calculados de un reporte")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Reportes>> actualizar(@PathVariable Long id, @Valid @RequestBody Reportes reporte) {
        Reportes actualizado = reportesService.actualizarReporte(id, reporte)
                .orElseThrow(() -> new RuntimeException("No se pudo actualizar. Reporte no encontrado con el ID: " + id));

        EntityModel<Reportes> recurso = EntityModel.of(actualizado,
                linkTo(methodOn(ReportesController.class).obtenerUno(id)).withSelfRel(),
                linkTo(methodOn(ReportesController.class).listar()).withRel("todos-los-reportes"));
        
        return ResponseEntity.ok(recurso);
    }

    @Operation(summary = "Eliminar un reporte del sistema", description = "Borra físicamente un reporte de la base de datos")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!reportesService.eliminarReporte(id)) {
            throw new RuntimeException("No se encontró el reporte a eliminar con el ID: " + id);
        }
        return ResponseEntity.noContent().build();
    }
}