package UniAttend.service;

import UniAttend.request.ReqResMateria;
import UniAttend.entity.Materia;
import UniAttend.repository.MateriaRepository;
import UniAttend.repository.ProgramacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private ProgramacionRepository programacionAcademicaRepository;

    public ReqResMateria guardar(ReqResMateria registroMateria) {
        ReqResMateria respuesta = new ReqResMateria();

        try {
            Materia materia = new Materia();
            materia.setNombre(registroMateria.getNombre());

            // Guardar la materia en la base de datos
            Materia materiaGuardada = materiaRepository.save(materia);

            // Comprobar si se guardó correctamente
            if (materiaGuardada.getId() != null) {
                respuesta.setMateria(materiaGuardada);
                respuesta.setMessage("Materia guardada exitosamente");
                respuesta.setStatusCode(200);
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }

    public ReqResMateria actualizar(Long id, Materia updatedMateria) {
        ReqResMateria respuesta = new ReqResMateria();

        try {
            Optional<Materia> optionalMateria = materiaRepository.findById(id);
            if (optionalMateria.isPresent()) {
                Materia materiaExistente = optionalMateria.get();

                // Actualizar los campos de la materia con los valores de la materia actualizada
                materiaExistente.setNombre(updatedMateria.getNombre());

                // Guardar los cambios en la base de datos
                Materia materiaGuardada = materiaRepository.save(materiaExistente);

                respuesta.setMateria(materiaGuardada);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Materia actualizada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Materia no encontrada para actualizar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al actualizar la materia: " + e.getMessage());
        }
        return respuesta;
    }


    public ReqResMateria eliminar(Long id) {
        ReqResMateria respuesta = new ReqResMateria();

        try {
            Optional<Materia> optionalMateria = materiaRepository.findById(id);
            if (optionalMateria.isPresent()) {
                materiaRepository.deleteById(id);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Materia eliminada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Materia no encontrada para eliminar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al eliminar la materia: " + e.getMessage());
        }
        return respuesta;
    }

    public ReqResMateria listar() {
        ReqResMateria respuesta = new ReqResMateria();

        try {
            List<Materia> materias = (List<Materia>) materiaRepository.findAll();
            respuesta.setMateriaList(materias);
            respuesta.setMessage("Materias recuperadas exitosamente");
            respuesta.setStatusCode(200);
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }

    public ReqResMateria getMateriaById(Long materiaId) {
        ReqResMateria respuesta = new ReqResMateria();
        try {
            Optional<Materia> optionalMateria = materiaRepository.findById(materiaId);
            if (optionalMateria.isPresent()) {
                Materia materia = optionalMateria.get();
                respuesta.setMateria(materia);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Materia con id '" + materiaId + "' encontrada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Materia con id '" + materiaId + "' no encontrada");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al recuperar la materia: " + e.getMessage());
        }
        return respuesta;
    }
}
