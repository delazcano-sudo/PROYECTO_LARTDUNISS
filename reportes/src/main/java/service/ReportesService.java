package service;

import org.springframework.lang.NonNull;
import model.Reportes;
import repository.ReportesRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReportesService {

    private final ReportesRepository reportesRepository;

    public ReportesService(ReportesRepository reportesRepository) {
        this.reportesRepository = reportesRepository;
    }

    public List<Reportes> obtenerTodos() {
        return reportesRepository.findAll();
    }

    public Reportes guardarReporte(@NonNull Reportes reporte) {
        return reportesRepository.save(reporte);
    }

    public Optional<Reportes> buscarPorId(@NonNull Long id) {
        return reportesRepository.findById(id);
    }

    public Optional<Reportes> actualizarReporte(@NonNull Long id, Reportes reporteActualizado) {
        return reportesRepository.findById(id).map((@NonNull Reportes reporteExistente) -> {
            reporteExistente.setTitulo(reporteActualizado.getTitulo());
            reporteExistente.setTipo(reporteActualizado.getTipo());
            reporteExistente.setFechaGeneracion(reporteActualizado.getFechaGeneracion());
            reporteExistente.setMontoTotalCalculado(reporteActualizado.getMontoTotalCalculado());
            return reportesRepository.save(reporteExistente);
        });
    }

    public boolean eliminarReporte(@NonNull Long id) {
        return reportesRepository.findById(id).map((@NonNull Reportes reporte) -> {
            reportesRepository.delete(reporte);
            return true;
        }).orElse(false);
    }
}