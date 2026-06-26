package service;

import model.Notificacion;
import repository.NotificacionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public NotificacionService(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    public List<Notificacion> obtenerTodas() {
        return notificacionRepository.findAll();
    }

    public Notificacion guardarNotificacion(Notificacion notificacion) {
        if (notificacion.getFechaEnvio() == null) {
            notificacion.setFechaEnvio(LocalDateTime.now());
        }
        return Objects.requireNonNull(notificacionRepository.save(notificacion), "La notificación guardada no puede ser null");
    }

    public Optional<Notificacion> buscarPorId(Long id) {
        return notificacionRepository.findById(id);
    }

    public Optional<Notificacion> actualizarNotificacion(Long id, Notificacion notificacionActualizada) {
        return notificacionRepository.findById(id).map((Notificacion notificacionExistente) -> {
            notificacionExistente.setDestinatario(notificacionActualizada.getDestinatario());
            notificacionExistente.setTipo(notificacionActualizada.getTipo());
            notificacionExistente.setMensaje(notificacionActualizada.getMensaje());
            notificacionExistente.setFechaEnvio(notificacionActualizada.getFechaEnvio());
            return notificacionRepository.save(notificacionExistente);
        });
    }

    public boolean eliminarNotificacion(Long id) {
        return notificacionRepository.findById(id).map((Notificacion notificacion) -> {
            notificacionRepository.delete(notificacion);
            return true;
        }).orElse(false);
    }
}