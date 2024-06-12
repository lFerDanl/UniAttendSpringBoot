package UniAttend.repository;

import UniAttend.entity.ProgramacionAcademica;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProgramacionRepository extends CrudRepository<ProgramacionAcademica, Long> {
    List<ProgramacionAcademica> findByUsuarioId(Long usuarioId);
}
