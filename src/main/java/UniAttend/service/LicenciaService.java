package UniAttend.service;

import UniAttend.request.ReqResLicencia;
import UniAttend.entity.Licencia;
import UniAttend.repository.LicenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LicenciaService {

    @Autowired
    private LicenciaRepository licenciaRepository;

    public ReqResLicencia guardar(ReqResLicencia registroLicencia) {
        ReqResLicencia respuesta = new ReqResLicencia();

        try {
            Licencia licencia = new Licencia();
            licencia.setTipo(registroLicencia.getTipo());
            licencia.setFechaInicio(registroLicencia.getFechaInicio());
            licencia.setFechaFin(registroLicencia.getFechaFin());
            licencia.setEstado(registroLicencia.getEstado());

            // Asignar la relación con ProgramacionAcademica y Usuario
            licencia.setProgramacionAcademica(registroLicencia.getProgramacionAcademica());
            licencia.setUsuario(registroLicencia.getUsuario());

            // Guardar la licencia en la base de datos
            Licencia licenciaGuardada = licenciaRepository.save(licencia);

            // Comprobar si se guardó correctamente
            if (licenciaGuardada.getId() != null) {
                respuesta.setLicencia(licenciaGuardada);
                respuesta.setMessage("Licencia guardada exitosamente");
                respuesta.setStatusCode(200);
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }

    public ReqResLicencia actualizar(Long id, Licencia licenciaActualizada) {
        ReqResLicencia respuesta = new ReqResLicencia();

        try {
            Optional<Licencia> optionalLicencia = licenciaRepository.findById(id);
            if (optionalLicencia.isPresent()) {
                Licencia licenciaExistente = optionalLicencia.get();
                licenciaExistente.setTipo(licenciaActualizada.getTipo());
                licenciaExistente.setFechaInicio(licenciaActualizada.getFechaInicio());
                licenciaExistente.setFechaFin(licenciaActualizada.getFechaFin());
                licenciaExistente.setEstado(licenciaActualizada.getEstado());

                // Asignar la relación con ProgramacionAcademica y Usuario
                licenciaExistente.setProgramacionAcademica(licenciaActualizada.getProgramacionAcademica());
                licenciaExistente.setUsuario(licenciaActualizada.getUsuario());

                // Guardar los cambios en la base de datos
                Licencia licenciaGuardada = licenciaRepository.save(licenciaExistente);

                respuesta.setLicencia(licenciaGuardada);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Licencia actualizada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Licencia no encontrada para actualizar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al actualizar la licencia: " + e.getMessage());
        }
        return respuesta;
    }

    public ReqResLicencia eliminar(Long id) {
        ReqResLicencia respuesta = new ReqResLicencia();

        try {
            Optional<Licencia> optionalLicencia = licenciaRepository.findById(id);
            if (optionalLicencia.isPresent()) {
                licenciaRepository.deleteById(id);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Licencia eliminada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Licencia no encontrada para eliminar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al eliminar la licencia: " + e.getMessage());
        }
        return respuesta;
    }

    public ReqResLicencia listar() {
        ReqResLicencia respuesta = new ReqResLicencia();

        try {
            List<Licencia> licencias = (List<Licencia>) licenciaRepository.findAll();
            respuesta.setLicenciaList(licencias);
            respuesta.setMessage("Licencias recuperadas exitosamente");
            respuesta.setStatusCode(200);
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }

    public ReqResLicencia getLicenciaById(Long licenciaId) {
        ReqResLicencia respuesta = new ReqResLicencia();
        try {
            Optional<Licencia> optionalLicencia = licenciaRepository.findById(licenciaId);
            if (optionalLicencia.isPresent()) {
                Licencia licencia = optionalLicencia.get();
                respuesta.setLicencia(licencia);
                respuesta.setStatusCode(200);
                respuesta.setMessage("Licencia con id '" + licenciaId + "' encontrada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Licencia con id '" + licenciaId + "' no encontrada");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al recuperar la licencia: " + e.getMessage());
        }
        return respuesta;
    }
}
