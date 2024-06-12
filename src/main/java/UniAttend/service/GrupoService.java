package UniAttend.service;

import UniAttend.request.ReqResGrupo;
import UniAttend.entity.Grupo;
import UniAttend.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public ReqResGrupo guardar(ReqResGrupo registroGrupo) {
        ReqResGrupo respuesta = new ReqResGrupo();

        try {
            Grupo grupo = new Grupo();
            grupo.setNombre(registroGrupo.getNombre());

            // Guardar el grupo en la base de datos
            Grupo grupoGuardado = grupoRepository.save(grupo);

            // Comprobar si se guardó correctamente
            if (grupoGuardado.getId() != null) {
                respuesta.setGrupo(grupoGuardado);
                respuesta.setMessage("Grupo guardado exitosamente");
                respuesta.setStatusCode(200);
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }

    public ReqResGrupo actualizar(Long id, Grupo updatedGrupo) {
        ReqResGrupo respuesta = new ReqResGrupo();

        try {
            Optional<Grupo> optionalGrupo = grupoRepository.findById(id);
            if (optionalGrupo.isPresent()) {
                Grupo grupoExistente = optionalGrupo.get();

                // Actualizar los campos del grupo con los valores del grupo actualizado
                grupoExistente.setNombre(updatedGrupo.getNombre());
                // Actualizar otras propiedades según la lógica de tu aplicación

                // Guardar los cambios en la base de datos
                Grupo grupoGuardado = grupoRepository.save(grupoExistente);

                respuesta.setGrupo(grupoGuardado);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Grupo actualizado exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Grupo no encontrado para actualizar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al actualizar el grupo: " + e.getMessage());
        }
        return respuesta;
    }

    public ReqResGrupo eliminar(Long id) {
        ReqResGrupo respuesta = new ReqResGrupo();

        try {
            Optional<Grupo> optionalGrupo = grupoRepository.findById(id);
            if (optionalGrupo.isPresent()) {
                grupoRepository.deleteById(id);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Grupo eliminado exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Grupo no encontrado para eliminar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al eliminar el grupo: " + e.getMessage());
        }
        return respuesta;
    }

    public ReqResGrupo listar() {
        ReqResGrupo respuesta = new ReqResGrupo();

        try {
            List<Grupo> grupos = (List<Grupo>) grupoRepository.findAll();
            respuesta.setGrupoList(grupos);
            respuesta.setMessage("Grupos recuperados exitosamente");
            respuesta.setStatusCode(200);
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }

    public ReqResGrupo getGrupoById(Long grupoId) {
        ReqResGrupo respuesta = new ReqResGrupo();
        try {
            Optional<Grupo> optionalGrupo = grupoRepository.findById(grupoId);
            if (optionalGrupo.isPresent()) {
                Grupo grupo = optionalGrupo.get();
                respuesta.setGrupo(grupo);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Grupo con ID '" + grupoId + "' encontrado exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Grupo con ID '" + grupoId + "' no encontrado");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al recuperar el grupo: " + e.getMessage());
        }
        return respuesta;
    }




}
