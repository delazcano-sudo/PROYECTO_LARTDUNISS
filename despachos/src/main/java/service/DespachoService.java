package service;

import model.Despacho;
import repository.DespachoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class DespachoService {

    @Autowired
    private DespachoRepository despachoRepository;

    public List<Despacho> obtenerTodos() {
        return despachoRepository.findAll();
    }

    public Despacho guardarDespacho(Despacho despacho) {
        return despachoRepository.save(despacho);
    }

    public Optional<Despacho> buscarPorId(Long id) {
        return despachoRepository.findById(id);
    }

    public Optional<Despacho> actualizarDespacho(Long id, Despacho despachoActualizado) {
        return despachoRepository.findById(id).map(despachoExistente -> {
            despachoExistente.setDireccionEntrega(despachoActualizado.getDireccionEntrega());
            despachoExistente.setEstado(despachoActualizado.getEstado());
            despachoExistente.setFechaProgramada(despachoActualizado.getFechaProgramada());
            despachoExistente.setCostoEnvio(despachoActualizado.getCostoEnvio());
            return despachoRepository.save(despachoExistente);
        });
    }

    public boolean eliminarDespacho(Long id) {
        return despachoRepository.findById(id).map(despacho -> {
            despachoRepository.delete(despacho);
            return true;
        }).orElse(false);
    }
}