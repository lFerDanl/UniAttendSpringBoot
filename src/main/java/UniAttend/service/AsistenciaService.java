package UniAttend.service;

import UniAttend.dto.AsistenciaDTO;
import UniAttend.dto.AulaDTO;
import UniAttend.entity.*;
import UniAttend.repository.ProgramacionHrRepository;
import UniAttend.repository.ProgramacionRepository;
import UniAttend.repository.UserRepository;
import UniAttend.request.ReqResAsistencia;
import UniAttend.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private ProgramacionHrRepository programacionHorarioRepository;

    @Autowired
    private ProgramacionRepository programacionRepository;

    @Autowired
    private UserRepository userRepository;

    public ReqResAsistencia registrarAsistencia(Long programacionHorarioId, Long usuarioId) {
        ReqResAsistencia response = new ReqResAsistencia();
        try {
            LocalDate fechaActual = LocalDate.now();
            LocalTime horaActual = LocalTime.now().truncatedTo(ChronoUnit.SECONDS); // Obtener la hora actual

            Optional<ProgramacionHorario> optionalProgramacionHorario = programacionHorarioRepository.findById(programacionHorarioId);
            if (optionalProgramacionHorario.isPresent()) {
                ProgramacionHorario programacionHorario = optionalProgramacionHorario.get();
                Horario horario = programacionHorario.getHorario();

                // Verificar que el día actual coincida con el día de la programación horaria
                String diaActual = obtenerDiaEnEspanol(fechaActual.getDayOfWeek().toString().toUpperCase());
                if (!horario.getDia().equalsIgnoreCase(diaActual)) {
                    response.setStatusCode(400);
                    response.setMessage("Hoy no hay clases programadas para esta programación horaria");
                    return response;
                }

                // Convertir horarios a LocalTime
                LocalTime horarioInicio = LocalTime.parse(horario.getHorarioInicio());
                LocalTime horarioFin = LocalTime.parse(horario.getHorarioFin());

                // Verificar si la hora actual está dentro del intervalo de tiempo de la clase
                if (horaActual.isBefore(horarioInicio) || horaActual.isAfter(horarioFin)) {
                    response.setStatusCode(400);
                    response.setMessage("No es la hora de esta clase. La clase es de " + horarioInicio + " a " + horarioFin);
                    return response;
                }

                // Buscar asistencias registradas para la fecha actual y esta programación horaria dentro del intervalo de tiempo
                List<Asistencia> asistencias = asistenciaRepository.findByProgramacionHorarioAndFecha(programacionHorario, fechaActual);
                boolean asistenciaRegistrada = asistencias.stream()
                        .anyMatch(a -> a.getProgramacionHorario().equals(programacionHorario) &&
                                !horaActual.isBefore(horarioInicio) &&
                                !horaActual.isAfter(horarioFin));

                if (!asistenciaRegistrada) {
                    Asistencia asistencia = new Asistencia();
                    asistencia.setFecha(fechaActual);
                    asistencia.setHora(horaActual);

                    // Verificar si la asistencia es un retraso
                    if (horaActual.isAfter(horarioInicio.plusMinutes(15))) {
                        asistencia.setEstado("RETRASO");
                    } else {
                        asistencia.setEstado("PRESENTE");
                    }

                    asistencia.setProgramacionHorario(programacionHorario);
                    System.out.println("Usuario ID para asistencia: " + usuarioId);
                    Optional<UserEntity> optionalUserEntity = userRepository.findById(usuarioId);
                    UserEntity user = optionalUserEntity.get();
                    asistencia.setUsuario(user);
                    Asistencia asistenciaResult = asistenciaRepository.save(asistencia);
                    marcarFaltas();
                    response.setAsistencia(new AsistenciaDTO(asistenciaResult));
                    response.setStatusCode(200);
                    response.setMessage("Asistencia registrada correctamente");
                } else {
                    response.setStatusCode(409);
                    response.setMessage("La asistencia ya fue registrada para esta clase hoy");
                }
            } else {
                response.setStatusCode(404);
                response.setMessage("Programación de horario no encontrada");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    // Nuevo método para marcar faltas
    @Scheduled(cron = "0 0 0 * * ?") // Este cron se ejecuta todos los días a la medianoche
    public void marcarFaltas() {
        LocalDate fechaAnterior = LocalDate.now().minusDays(1); // Verificar las faltas del día anterior
        String diaAnterior = obtenerDiaEnEspanol(fechaAnterior.getDayOfWeek().toString().toUpperCase());

        // Obtener todas las programaciones académicas
        List<ProgramacionAcademica> programacionesAcademicas = (List<ProgramacionAcademica>) programacionRepository.findAll();

        for (ProgramacionAcademica programacionAcademica : programacionesAcademicas) {
            List<ProgramacionHorario> programacionesHorarios = programacionAcademica.getProgramacionHorarios();
            UserEntity docente = programacionAcademica.getUsuario();

            for (ProgramacionHorario programacionHorario : programacionesHorarios) {
                Horario horario = programacionHorario.getHorario();
                if (horario.getDia().equalsIgnoreCase(diaAnterior)) {
                    LocalTime horarioInicio = LocalTime.parse(horario.getHorarioInicio());
                    LocalTime horarioFin = LocalTime.parse(horario.getHorarioFin());

                    // Buscar todas las asistencias registradas para esta programación horaria en la fecha anterior
                    List<Asistencia> asistencias = asistenciaRepository.findByProgramacionHorarioAndFecha(programacionHorario, fechaAnterior);

                    boolean asistenciaRegistrada = asistencias.stream()
                            .anyMatch(a -> !a.getHora().isBefore(horarioInicio) && !a.getHora().isAfter(horarioFin));

                    if (!asistenciaRegistrada) {
                        Asistencia falta = new Asistencia();
                        falta.setFecha(fechaAnterior);
                        falta.setHora(horarioFin); // Registrar la falta al final del horario
                        falta.setEstado("FALTA");
                        falta.setProgramacionHorario(programacionHorario);
                        falta.setUsuario(docente);
                        asistenciaRepository.save(falta);
                    }
                }
            }
        }
    }


    public ReqResAsistencia getAsistenciaDeHoy(Long programacionHorarioId) {
        ReqResAsistencia response = new ReqResAsistencia();
        try {
            LocalDate fechaActual = LocalDate.now();
            LocalTime horaActual = LocalTime.now(); // Obtener la hora actual

            Optional<ProgramacionHorario> optionalProgramacionHorario = programacionHorarioRepository.findById(programacionHorarioId);
            if (optionalProgramacionHorario.isPresent()) {
                ProgramacionHorario programacionHorario = optionalProgramacionHorario.get();
                Horario horario = programacionHorario.getHorario();

                // Verificar que el día actual coincida con el día de la programación horaria
                String diaActual = obtenerDiaEnEspanol(fechaActual.getDayOfWeek().toString().toUpperCase());
                if (!horario.getDia().equalsIgnoreCase(diaActual)) {
                    response.setStatusCode(400);
                    response.setMessage("Hoy no hay clases programadas para esta programación horaria");
                    return response;
                }

                // Convertir horarios a LocalTime

                LocalTime horarioInicio = LocalTime.parse(horario.getHorarioInicio());
                LocalTime horarioFin = LocalTime.parse(horario.getHorarioFin());

                // Buscar asistencias registradas para la fecha actual y esta programación horaria
                List<Asistencia> asistencias = asistenciaRepository.findByProgramacionHorarioAndFecha(programacionHorario, fechaActual);
                boolean asistenciaEncontrada = asistencias.stream()
                        .anyMatch(a -> a.getProgramacionHorario().equals(programacionHorario) &&
                                !horaActual.isBefore(horarioInicio) &&
                                !horaActual.isAfter(horarioFin));
                if (asistenciaEncontrada) {
                    Asistencia asistencia = asistencias.stream()
                            .filter(a -> !horaActual.isBefore(horarioInicio) &&
                                    !horaActual.isAfter(horarioFin))
                            .findFirst()
                            .orElse(null);

                    if (asistencia != null) {
                        response.setAsistencia(new AsistenciaDTO(asistencia));
                        response.setStatusCode(200);
                        response.setMessage("Asistencia encontrada correctamente");
                    } else {
                        AsistenciaDTO ausente = new AsistenciaDTO();
                        ausente.setEstado("ausente");
                        response.setAsistencia(ausente);
                        response.setStatusCode(200);
                        response.setMessage("Asistencia no encontrada para este intervalo de tiempo");
                    }
                } else {
                    AsistenciaDTO ausente = new AsistenciaDTO();
                    ausente.setEstado("AUSENTE");
                    response.setAsistencia(ausente);
                    response.setStatusCode(200);
                    response.setMessage("No se encontraron asistencias para esta programación horaria hoy");
                }
            } else {
                response.setStatusCode(404);
                response.setMessage("Programación de horario no encontrada");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    private String obtenerDiaEnEspanol(String diaEnIngles) {
        switch (diaEnIngles) {
            case "MONDAY":
                return "Lunes";
            case "TUESDAY":
                return "Martes";
            case "WEDNESDAY":
                return "Miércoles";
            case "THURSDAY":
                return "Jueves";
            case "FRIDAY":
                return "Viernes";
            case "SATURDAY":
                return "Sábado";
            case "SUNDAY":
                return "Domingo";
            default:
                return diaEnIngles; // Por si acaso no se encuentra, se devuelve el nombre original
        }
    }

    public ReqResAsistencia listar() {
        ReqResAsistencia respuesta = new ReqResAsistencia();
        try {
            List<Asistencia> asistencias = (List<Asistencia>) asistenciaRepository.findAll();
            List<AsistenciaDTO> asistenciaDTOs = asistencias.stream().map(AsistenciaDTO::new).collect(Collectors.toList());
            respuesta.setAsistenciaList(asistenciaDTOs);
            respuesta.setMessage("Asistencias recuperadas exitosamente");
            respuesta.setStatusCode(200);
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setError(e.getMessage());
        }
        return respuesta;
    }

    public ReqResAsistencia getAsistenciaById(Long asistenciaId) {
        ReqResAsistencia respuesta = new ReqResAsistencia();
        try {
            Optional<Asistencia> optionalAsistencia = asistenciaRepository.findById(asistenciaId);
            if (optionalAsistencia.isPresent()) {
                Asistencia asistencia = optionalAsistencia.get();
                respuesta.setAsistencia(new AsistenciaDTO(asistencia));
                respuesta.setStatusCode(200);
                respuesta.setMessage("Asistencia con id '" + asistenciaId + "' encontrada exitosamente");
            } else {
                respuesta.setStatusCode(404);
                respuesta.setMessage("Asistencia con id '" + asistenciaId + "' no encontrada");
            }
        } catch (Exception e) {
            respuesta.setStatusCode(500);
            respuesta.setMessage("Error al recuperar la asistencia: " + e.getMessage());
        }
        return respuesta;
    }
}