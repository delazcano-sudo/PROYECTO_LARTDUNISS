package controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import model.Notificacion;
import service.NotificacionService;

@RestController
@RequestMapping("/api/v1/notificaciones") // Con esta ruta mas el puerto nos metemos en postman <3
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @GetMapping
    public ResponseEntity<List<Notificacion>> listar() {
        List<Notificacion> lista = notificacionService.obtenerTodas();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Notificacion> crear(@Valid @RequestBody Notificacion notificacion) {
        Notificacion nueva = notificacionService.guardarNotificacion(notificacion);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> obtenerUna(@PathVariable Long id) {
        return notificacionService.buscarPorId(id)
                .map(n -> new ResponseEntity<>(n, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> actualizar(@PathVariable Long id, @Valid @RequestBody Notificacion notificacion) {
        return notificacionService.actualizarNotificacion(id, notificacion)
                .map(n -> new ResponseEntity<>(n, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        if (notificacionService.eliminarNotificacion(id)) {
            return new ResponseEntity<>("Notificación eliminada correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("No se encontró la notificación a eliminar", HttpStatus.NOT_FOUND);
    }
}