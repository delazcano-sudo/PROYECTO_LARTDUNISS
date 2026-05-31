package service;

import model.Reportes;
import repository.ReportesRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@Service
public class ReportesService {

    @Autowired
    private ReportesRepository reportesRepository;

    public List<Reportes> obtenerTodos() {
        return reportesRepository.findAll();
    }

    public Reportes guardarReporte(Reportes reporte) {
        return reportesRepository.save(reporte);
    }

    public Optional<Reportes> buscarPorId(Long id) {
        return reportesRepository.findById(id);
    }

    public Optional<Reportes> actualizarReporte(Long id, Reportes reporteActualizado) {
        return reportesRepository.findById(id).map(reporteExistente -> {
            reporteExistente.setTitulo(reporteActualizado.getTitulo());
            reporteExistente.setTipo(reporteActualizado.getTipo());
            reporteExistente.setFechaGeneracion(reporteActualizado.getFechaGeneracion());
            reporteExistente.setMontoTotalCalculado(reporteActualizado.getMontoTotalCalculado());
            return reportesRepository.save(reporteExistente);
        });
    }

    public boolean eliminarReporte(Long id) {
        return reportesRepository.findById(id).map(reporte -> {
            reportesRepository.delete(reporte);
            return true;
        }).orElse(false);
    }
}