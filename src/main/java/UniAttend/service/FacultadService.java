package UniAttend.service;

import UniAttend.request.ReqResFacultad;
import UniAttend.entity.Facultad;
import UniAttend.repository.CarreraRepository;
import UniAttend.repository.FacultadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultadService {

    @Autowired
    private FacultadRepository facultadRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    public ReqResFacultad guardar(ReqResFacultad registroFacultad) {
        ReqResFacultad respuesta = new ReqResFacultad();

        try {
            Facultad facultad = new Facultad();
            facultad.setNombre(registroFacultad.getNombre());

            // Guardar la facultad en la base de datos
            Facultad facultadGuardada = facultadRepository.save(facultad);

            // Comprobar si se guardó correctamente
            if (facultadGuardada.getId() != null) {
                respuesta.setFacultad(facultadGuardada);
                respuesta.setMessage("Facultad guardada exitosamente");
                respuesta.setStatusCode(200);
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }

    public ReqResFacultad actualizar(Long id, Facultad facultadActualizada) {
        ReqResFacultad respuesta = new ReqResFacultad();

        try {
            Optional<Facultad> optionalFacultad = facultadRepository.findById(id);
            if (optionalFacultad.isPresent()) {
                Facultad facultadExistente = optionalFacultad.get();
                facultadExistente.setNombre(facultadActualizada.getNombre());
                // Actualiza otras propiedades según la lógica de tu aplicación

                // Guardar los cambios en la base de datos
                Facultad facultadGuardada = facultadRepository.save(facultadExistente);

                respuesta.setFacultad(facultadGuardada);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Facultad actualizada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Facultad no encontrada para actualizar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al actualizar la facultad: " + e.getMessage());
        }
        return respuesta;
    }

    public ReqResFacultad eliminar(Long id) {
        ReqResFacultad respuesta = new ReqResFacultad();

        try {
            Optional<Facultad> optionalFacultad = facultadRepository.findById(id);
            if (optionalFacultad.isPresent()) {
                facultadRepository.deleteById(id);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Facultad eliminada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Facultad no encontrada para eliminar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al eliminar la facultad: " + e.getMessage());
        }
        return respuesta;
    }

    public ReqResFacultad listar() {
        ReqResFacultad resp = new ReqResFacultad();

        try {
            List<Facultad> facultades = (List<Facultad>) facultadRepository.findAll();
            resp.setFacultadList(facultades);
            resp.setMessage("Facultades recuperadas exitosamente");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqResFacultad getFacultadById(Long facultadId) {
        ReqResFacultad resp = new ReqResFacultad();
        try {
            Optional<Facultad> optionalFacultad = facultadRepository.findById(facultadId);
            if (optionalFacultad.isPresent()) {
                Facultad facultad = optionalFacultad.get();
                resp.setFacultad(facultad);
                resp.setStatusCode(200);
                resp.setMessage("Facultad con id '" + facultadId + "' encontrada exitosamente");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Facultad con id '" + facultadId + "' no encontrada");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error ocurrido: " + e.getMessage());
        }
        return resp;
    }
}
