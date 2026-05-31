package repository;

import model.Reportes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportesRepository extends JpaRepository<Reportes, Long> {
}