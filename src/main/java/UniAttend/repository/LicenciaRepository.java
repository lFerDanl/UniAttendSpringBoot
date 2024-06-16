package UniAttend.repository;

import UniAttend.entity.Licencia;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LicenciaRepository extends CrudRepository<Licencia,Long> {
    List<Licencia> findByUsuarioId(Long usuarioId);
}
