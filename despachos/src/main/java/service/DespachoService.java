package service;

import model.Despacho;
import repository.DespachoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DespachoService {

    private final DespachoRepository despachoRepository;

    public DespachoService(DespachoRepository despachoRepository) {
        this.despachoRepository = despachoRepository;
    }

    public List<Despacho> obtenerTodos() {
        return despachoRepository.findAll();
    }

    public Despacho guardarDespacho(Despacho despacho) {
        return Objects.requireNonNull(despachoRepository.save(despacho), "El despacho guardado no puede ser null");
    }

    public Optional<Despacho> buscarPorId(Long id) {
        return despachoRepository.findById(id);
    }

    public Optional<Despacho> actualizarDespacho(Long id, Despacho despachoActualizado) {
        return despachoRepository.findById(id).map((Despacho despachoExistente) -> {
            despachoExistente.setDireccionEntrega(despachoActualizado.getDireccionEntrega());
            despachoExistente.setEstado(despachoActualizado.getEstado());
            despachoExistente.setFechaProgramada(despachoActualizado.getFechaProgramada());
            despachoExistente.setCostoEnvio(despachoActualizado.getCostoEnvio());
            return despachoRepository.save(despachoExistente);
        });
    }

    public boolean eliminarDespacho(Long id) {
        return despachoRepository.findById(id).map((Despacho despacho) -> {
            despachoRepository.delete(despacho);
            return true;
        }).orElse(false);
    }
}