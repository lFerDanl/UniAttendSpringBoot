package UniAttend.repository;

import UniAttend.entity.Asistencia;
import UniAttend.entity.ProgramacionAcademica;
import UniAttend.entity.ProgramacionHorario;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface AsistenciaRepository extends CrudRepository<Asistencia,Long> {
    List<Asistencia> findByProgramacionHorarioAndFecha(ProgramacionHorario programacionHorario, LocalDate fecha);
    List<Asistencia> findByUsuarioId(Long usuarioId);
}
