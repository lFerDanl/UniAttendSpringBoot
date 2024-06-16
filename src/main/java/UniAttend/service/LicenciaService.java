package UniAttend.service;

import UniAttend.dto.AulaDTO;
import UniAttend.dto.LicenciaDTO;
import UniAttend.entity.*;
import UniAttend.repository.AsistenciaRepository;
import UniAttend.repository.ProgramacionRepository;
import UniAttend.repository.UserRepository;
import UniAttend.request.ReqResLicencia;
import UniAttend.repository.LicenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LicenciaService {

    @Autowired
    private LicenciaRepository licenciaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProgramacionRepository programacionRepository;

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    public ReqResLicencia guardar(ReqResLicencia registroLicencia, Long userId) {
        ReqResLicencia respuesta = new ReqResLicencia();

        try {
            Licencia licencia = new Licencia();
            licencia.setTipo(registroLicencia.getTipo());
            licencia.setFechaInicio(registroLicencia.getFechaInicio());
            licencia.setFechaFin(registroLicencia.getFechaFin());
            licencia.setEstado("PENDIENTE");
            // Asignar la relación con ProgramacionAcademica y Usuario
            Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
            UserEntity usuario = optionalUserEntity.get();
            licencia.setUsuario(usuario);

            // Guardar la licencia en la base de datos
            Licencia licenciaGuardada = licenciaRepository.save(licencia);

            // Comprobar si se guardó correctamente
            if (licenciaGuardada.getId() != null) {
                respuesta.setLicencia(new LicenciaDTO(licenciaGuardada));
                respuesta.setMessage("Licencia guardada exitosamente");
                respuesta.setStatusCode(200);
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }

    public ReqResLicencia actualizar(Long id, ReqResLicencia licenciaActualizada) {
        ReqResLicencia respuesta = new ReqResLicencia();

        try {
            Optional<Licencia> optionalLicencia = licenciaRepository.findById(id);
            if (optionalLicencia.isPresent()) {
                Licencia licenciaExistente = optionalLicencia.get();
                licenciaExistente.setTipo(licenciaActualizada.getTipo());
                licenciaExistente.setFechaInicio(licenciaActualizada.getFechaInicio());
                licenciaExistente.setFechaFin(licenciaActualizada.getFechaFin());

                // Guardar los cambios en la base de datos
                Licencia licenciaGuardada = licenciaRepository.save(licenciaExistente);

                respuesta.setLicencia(new LicenciaDTO(licenciaGuardada));
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
            List<LicenciaDTO> licenciaDTOs = licencias.stream().map(LicenciaDTO::new).collect(Collectors.toList());
            respuesta.setLicenciaList(licenciaDTOs);
            respuesta.setMessage("Licencias recuperadas exitosamente");
            respuesta.setStatusCode(200);
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }

    public ReqResLicencia listarByUsuarioId(Long usuarioId) {
        ReqResLicencia respuesta = new ReqResLicencia();

        try {
            List<Licencia> licencias = licenciaRepository.findByUsuarioId(usuarioId);
            List<LicenciaDTO> licenciaDTOs = licencias.stream().map(LicenciaDTO::new).collect(Collectors.toList());
            respuesta.setLicenciaList(licenciaDTOs);
            respuesta.setMessage("Licencias recuperadas exitosamente por usuario ID: " + usuarioId);
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
                respuesta.setLicencia(new LicenciaDTO(licencia));
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
    public ReqResLicencia aprobarLicencia(Long licenciaId, ReqResLicencia estado) {
        ReqResLicencia respuesta = new ReqResLicencia();

        try {
            Optional<Licencia> optionalLicencia = licenciaRepository.findById(licenciaId);
            if (optionalLicencia.isPresent()) {
                Licencia licencia = optionalLicencia.get();

                if ("APROBADO".equals(estado.getEstado())) {
                    licencia.setEstado("APROBADO");
                    respuesta.setMessage("Licencia aprobada exitosamente");
                    // Registrar asistencias con estado LICENCIA
                    registrarAsistenciasPorLicencia(licencia);
                } else if ("DENEGADO".equals(estado.getEstado())) {
                    licencia.setEstado("DENEGADO");
                    respuesta.setMessage("Licencia denegada exitosamente");
                }

                // Guardar los cambios en la base de datos
                Licencia licenciaGuardada = licenciaRepository.save(licencia);

                respuesta.setLicencia(new LicenciaDTO(licenciaGuardada));
                respuesta.setStatusCode(200);
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Licencia no encontrada para aprobar o denegar");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al aprobar o denegar la licencia: " + e.getMessage());
        }
        return respuesta;
    }
    private void registrarAsistenciasPorLicencia(Licencia licencia) {
        UserEntity usuario = licencia.getUsuario();
        LocalDate fechaInicio = LocalDate.parse(licencia.getFechaInicio());
        LocalDate fechaFin = LocalDate.parse(licencia.getFechaFin());

        List<ProgramacionAcademica> programacionesAcademicas = programacionRepository.findByUsuarioId(usuario.getId());

        for (ProgramacionAcademica programacionAcademica : programacionesAcademicas) {
            List<ProgramacionHorario> programacionesHorarios = programacionAcademica.getProgramacionHorarios();

            for (ProgramacionHorario programacionHorario : programacionesHorarios) {
                Horario horario = programacionHorario.getHorario();
                LocalTime horarioInicio = LocalTime.parse(horario.getHorarioInicio());
                LocalTime horarioFin = LocalTime.parse(horario.getHorarioFin());

                for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
                    String dia = obtenerDiaEnEspanol(fecha.getDayOfWeek().toString().toUpperCase());
                    if (horario.getDia().equalsIgnoreCase(dia)) {
                        Asistencia asistencia = new Asistencia();
                        asistencia.setFecha(fecha);
                        asistencia.setHora(horarioInicio);
                        asistencia.setEstado("LICENCIA");
                        asistencia.setProgramacionHorario(programacionHorario);
                        asistencia.setUsuario(usuario);
                        asistenciaRepository.save(asistencia);
                    }
                }
            }
        }
    }

    private String obtenerDiaEnEspanol(String diaIngles) {
        switch (diaIngles) {
            case "MONDAY":
                return "LUNES";
            case "TUESDAY":
                return "MARTES";
            case "WEDNESDAY":
                return "MIERCOLES";
            case "THURSDAY":
                return "JUEVES";
            case "FRIDAY":
                return "VIERNES";
            case "SATURDAY":
                return "SABADO";
            case "SUNDAY":
                return "DOMINGO";
            default:
                return "";
        }
    }
}

