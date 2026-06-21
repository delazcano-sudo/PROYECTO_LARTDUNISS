package controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import model.Notificacion;
import service.NotificacionService;

// ANOTACIONES SWAGGER
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/notificaciones")
@Tag(name = "Controlador de Notificaciones", description = "Endpoints para el envío, registro y auditoría de alertas del sistema")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    @Operation(summary = "Listar todas las notificaciones", description = "Retorna una lista completa de todo el historial de alertas emitidas")
    @ApiResponse(responseCode = "200", description = "Lista de notificaciones obtenida con éxito")
    public ResponseEntity<List<Notificacion>> listar() {
        List<Notificacion> lista = notificacionService.obtenerTodas();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Crear y enviar una notificación", description = "Registra una alerta en el sistema asignándole la fecha actual si no se provee")
    @ApiResponse(responseCode = "201", description = "Notificación creada con éxito")
    @ApiResponse(responseCode = "400", description = "Cuerpo de la petición inválido o incompleto")
    public ResponseEntity<Notificacion> crear(@Valid @RequestBody Notificacion notificacion) {
        Notificacion nueva = notificacionService.guardarNotificacion(notificacion);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener notificación por ID", description = "Busca los detalles de una notificación específica a través de su ID único")
    @ApiResponse(responseCode = "200", description = "Notificación encontrada")
    @ApiResponse(responseCode = "404", description = "Notificación no encontrada", 
                 content = @Content(schema = @Schema(implementation = exception.ErrorResponse.class)))
    public ResponseEntity<Notificacion> obtenerUna(@PathVariable Long id) {
        Notificacion notificacion = notificacionService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("La notificación con ID " + id + " no existe en el sistema."));
        return new ResponseEntity<>(notificacion, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una notificación existente", description = "Modifica los valores de destinatario, tipo, mensaje o fecha buscando por ID")
    @ApiResponse(responseCode = "200", description = "Notificación actualizada de forma exitosa")
    @ApiResponse(responseCode = "404", description = "La notificación con el ID indicado no existe", 
                 content = @Content(schema = @Schema(implementation = exception.ErrorResponse.class)))
    public ResponseEntity<Notificacion> actualizar(@PathVariable Long id, @Valid @RequestBody Notificacion notificacion) {
        Notificacion actualizada = notificacionService.actualizarNotificacion(id, notificacion)
                .orElseThrow(() -> new RuntimeException("No se pudo actualizar. La notificación con ID " + id + " no existe en el sistema."));
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una notificación", description = "Remueve de manera permanente el registro del historial mediante su ID")
    @ApiResponse(responseCode = "200", description = "Notificación miembro eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró el registro para eliminar", 
                 content = @Content(schema = @Schema(implementation = exception.ErrorResponse.class)))
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (!notificacionService.eliminarNotificacion(id)) {
            throw new RuntimeException("No se encontró la notificación a eliminar con el ID: " + id);
        }
        return new ResponseEntity<>("Notificación eliminada correctamente", HttpStatus.OK);
    }
}