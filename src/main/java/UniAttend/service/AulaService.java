package UniAttend.service;

import UniAttend.dto.AulaDTO;
import UniAttend.request.ReqResAula;
import UniAttend.entity.Aula;
import UniAttend.repository.AulaRepository;
import UniAttend.repository.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private ModuloRepository moduloRepository;

    public ReqResAula guardar(ReqResAula registroAula) {
        ReqResAula respuesta = new ReqResAula();

        try {
            Aula aula = new Aula();
            aula.setNro(registroAula.getNro());
            aula.setCapacidad(registroAula.getCapacidad());

            // Guardar el aula en la base de datos
            Aula aulaGuardada = aulaRepository.save(aula);

            // Comprobar si se guardó correctamente
            if (aulaGuardada.getId() != null) {
                AulaDTO aulaDTO = new AulaDTO(aulaGuardada); // Convertir la entidad a DTO
                respuesta.setAula(aulaDTO);
                respuesta.setMessage("Aula guardada exitosamente");
                respuesta.setStatusCode(200);
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }

    public ReqResAula actualizar(Long id, ReqResAula aulaActualizada) {
        ReqResAula respuesta = new ReqResAula();

        try {
            Optional<Aula> optionalAula = aulaRepository.findById(id);
            if (optionalAula.isPresent()) {
                Aula aulaExistente = optionalAula.get();
                aulaExistente.setNro(aulaActualizada.getNro());
                aulaExistente.setCapacidad(aulaActualizada.getCapacidad());

                // Guardar los cambios en la base de datos
                Aula aulaGuardada = aulaRepository.save(aulaExistente);

                AulaDTO aulaDTO = new AulaDTO(aulaGuardada);

                respuesta.setAula(aulaDTO);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Aula actualizada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Aula no encontrada para actualizar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al actualizar el aula: " + e.getMessage());
        }
        return respuesta;
    }

    public ReqResAula eliminar(Long id) {
        ReqResAula respuesta = new ReqResAula();

        try {
            Optional<Aula> optionalAula = aulaRepository.findById(id);
            if (optionalAula.isPresent()) {
                aulaRepository.deleteById(id);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Aula eliminada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Aula no encontrada para eliminar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al eliminar el aula: " + e.getMessage());
        }
        return respuesta;
    }

    public ReqResAula listar() {
        ReqResAula resp = new ReqResAula();

        try {
            List<Aula> aulas = (List<Aula>) aulaRepository.findAll();
            List<AulaDTO> aulaDTOs = aulas.stream().map(AulaDTO::new).collect(Collectors.toList());
            resp.setAulaList(aulaDTOs);
            resp.setMessage("Aulas recuperadas exitosamente");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqResAula getAulaById(Long aulaId) {
        ReqResAula resp = new ReqResAula();
        try {
            Optional<Aula> optionalAula = aulaRepository.findById(aulaId);
            if (optionalAula.isPresent()) {
                Aula aula = optionalAula.get();
                AulaDTO aulaDTO = new AulaDTO(aula);
                resp.setAula(aulaDTO);
                resp.setStatusCode(200);
                resp.setMessage("Aula con id '" + aulaId + "' encontrada exitosamente");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Aula con id '" + aulaId + "' no encontrada");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error ocurrido: " + e.getMessage());
        }
        return resp;
    }
}
