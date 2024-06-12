package UniAttend.repository;

import UniAttend.entity.ProgramacionAcademica;
import UniAttend.entity.ProgramacionHorario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProgramacionHrRepository extends CrudRepository<ProgramacionHorario, Long> {
    List<ProgramacionHorario> findByProgramacionAcademica(ProgramacionAcademica programacionAcademica);

    @Query("SELECT ph FROM ProgramacionHorario ph JOIN ph.horario h WHERE h.dia = :dia AND :horaActual BETWEEN h.horarioInicio AND h.horarioFin")
    List<ProgramacionHorario> findClasesByDiaAndHora(@Param("dia") String dia, @Param("horaActual") String horaActual);

    @Query("SELECT ph FROM ProgramacionHorario ph JOIN ph.horario h WHERE h.dia = :dia")
    List<ProgramacionHorario> findByDia(@Param("dia") String dia);

    List<ProgramacionHorario> findByHorarioDia(String dia);

}
