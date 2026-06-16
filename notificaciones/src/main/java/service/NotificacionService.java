package service;

import model.Notificacion;
import repository.NotificacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// Usamos la metodologia CRUD completa <3
@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    public List<Notificacion> obtenerTodas() {
        return notificacionRepository.findAll();
    }

    public Notificacion guardarNotificacion(Notificacion notificacion) {
        // Si no viene con fecha, le asignamos la hora actual automáticamente
        if (notificacion.getFechaEnvio() == null) {
            notificacion.setFechaEnvio(LocalDateTime.now());
        }
        return notificacionRepository.save(notificacion);
    }

    public Optional<Notificacion> buscarPorId(Long id) {
        return notificacionRepository.findById(id);
    }

    public Optional<Notificacion> actualizarNotificacion(Long id, Notificacion notificacionActualizada) {
        return notificacionRepository.findById(id).map(notificacionExistente -> {
            notificacionExistente.setDestinatario(notificacionActualizada.getDestinatario());
            notificacionExistente.setTipo(notificacionActualizada.getTipo());
            notificacionExistente.setMensaje(notificacionActualizada.getMensaje());
            notificacionExistente.setFechaEnvio(notificacionActualizada.getFechaEnvio());
            return notificacionRepository.save(notificacionExistente);
        });
    }

    public boolean eliminarNotificacion(Long id) {
        return notificacionRepository.findById(id).map(notificacion -> {
            notificacionRepository.delete(notificacion);
            return true;
        }).orElse(false);
    }
}