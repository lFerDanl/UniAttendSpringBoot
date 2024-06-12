package UniAttend.service;

import UniAttend.dto.AsistenciaDTO;
import UniAttend.dto.AulaDTO;
import UniAttend.entity.Horario;
import UniAttend.entity.ProgramacionHorario;
import UniAttend.repository.ProgramacionHrRepository;
import UniAttend.request.ReqResAsistencia;
import UniAttend.entity.Asistencia;
import UniAttend.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private ProgramacionHrRepository programacionHorarioRepository;

    public ReqResAsistencia registrarAsistencia(Long programacionHorarioId) {
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
                    asistencia.setEstado("PRESENTE");
                    asistencia.setProgramacionHorario(programacionHorario);
                    Asistencia asistenciaResult = asistenciaRepository.save(asistencia);

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