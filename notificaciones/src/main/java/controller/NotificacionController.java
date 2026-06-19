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
    @ApiResponse(responseCode = "404", description = "Notificación no localizada")
    public ResponseEntity<Notificacion> obtenerUna(@PathVariable Long id) {
        return notificacionService.buscarPorId(id)
                .map(n -> new ResponseEntity<>(n, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una notificación existente", description = "Modifica los valores de destinatario, tipo, mensaje o fecha buscando por ID")
    @ApiResponse(responseCode = "200", description = "Notificación actualizada de forma exitosa")
    @ApiResponse(responseCode = "404", description = "La notificación con el ID indicado no existe")
    public ResponseEntity<Notificacion> actualizar(@PathVariable Long id, @Valid @RequestBody Notificacion notificacion) {
        return notificacionService.actualizarNotificacion(id, notificacion)
                .map(n -> new ResponseEntity<>(n, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una notificación", description = "Remueve de manera permanente el registro del historial mediante su ID")
    @ApiResponse(responseCode = "200", description = "Notificación eliminada correctamente")
    @ApiResponse(responseCode = "404", description = "No se encontró el registro para eliminar")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (notificacionService.eliminarNotificacion(id)) {
            return new ResponseEntity<>("Notificación eliminada correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("No se encontró la notificación a eliminar", HttpStatus.NOT_FOUND);
    }
}