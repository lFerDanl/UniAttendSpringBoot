package UniAttend.service;

import UniAttend.entity.*;
import UniAttend.repository.*;
import UniAttend.request.ReqResProgramacionHr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProgramacionHrService {

    @Autowired
    private ProgramacionHrRepository programacionHrRepository;

    @Autowired
    private ProgramacionRepository programacionRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private ModuloRepository moduloRepository;

    @Autowired
    private AulaRepository aulaRepository;

    public ReqResProgramacionHr guardar(Long id, ReqResProgramacionHr registroRequest) {
        ReqResProgramacionHr resp = new ReqResProgramacionHr();

        try {
            Optional<ProgramacionAcademica> optionalProgramacion = programacionRepository.findById(id);
                if (optionalProgramacion.isPresent()) {
                    ProgramacionAcademica existingProgramacion = optionalProgramacion.get();

                    ProgramacionHorario programacionHorario = new ProgramacionHorario();
                    programacionHorario.setHorario(registroRequest.getHorario());
                    Optional<Horario> optionalHorario = horarioRepository.findById(registroRequest.getHorario().getId());
                    Horario horario = optionalHorario.get();
                    programacionHorario.setHorario(horario);
                    Optional<Modulo> optionalModulo = moduloRepository.findById(registroRequest.getModulo().getId());
                    Modulo modulo = optionalModulo.get();
                    programacionHorario.setModulo(modulo);
                    Optional<Aula> optionalAula = aulaRepository.findById(registroRequest.getAula().getId());
                    Aula aula = optionalAula.get();
                    programacionHorario.setAula(aula);
                    programacionHorario.setProgramacionAcademica(existingProgramacion);
                    // Guarda la programación horario
                    ProgramacionHorario programacionHorarioResult = programacionHrRepository.save(programacionHorario);

                    // Verifica si se guardó correctamente
                    if (programacionHorarioResult.getId() != null) {
                        resp.setProgramacionHorario(programacionHorarioResult);
                        resp.setMessage("Programación horario guardada exitosamente");
                        resp.setStatusCode(200);
                    }
                }else {
                    resp.setStatusCode(404);
                    resp.setMessage("Programación académica no encontrada para asignar");
                }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqResProgramacionHr actualizar(Long id, ReqResProgramacionHr updatedProgramacion) {
        ReqResProgramacionHr resp = new ReqResProgramacionHr();

        try {
            Optional<ProgramacionHorario> optionalProgramacionHorario = programacionHrRepository.findById(id);
            if (optionalProgramacionHorario.isPresent()) {
                ProgramacionHorario existingProgramacionHorario = optionalProgramacionHorario.get();
                existingProgramacionHorario.setHorario(updatedProgramacion.getHorario());
                Optional<Modulo> optionalModulo = moduloRepository.findById(updatedProgramacion.getModulo().getId());
                Modulo modulo = optionalModulo.get();
                existingProgramacionHorario.setModulo(modulo);
                Optional<Aula> optionalAula = aulaRepository.findById(updatedProgramacion.getAula().getId());
                Aula aula = optionalAula.get();
                existingProgramacionHorario.setAula(aula);

                // Guardar la programación horario actualizada
                ProgramacionHorario savedProgramacionHorario = programacionHrRepository.save(existingProgramacionHorario);

                resp.setProgramacionHorario(savedProgramacionHorario);
                resp.setStatusCode(200);
                resp.setMessage("Programación horario actualizada exitosamente");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Programación horario no encontrada para actualizar");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Ocurrió un error al actualizar la programación horario: " + e.getMessage());
        }
        return resp;
    }

    public ReqResProgramacionHr eliminar(Long id) {
        ReqResProgramacionHr resp = new ReqResProgramacionHr();

        try {
            Optional<ProgramacionHorario> optionalProgramacionHorario = programacionHrRepository.findById(id);
            if (optionalProgramacionHorario.isPresent()) {
                programacionHrRepository.deleteById(id);
                resp.setStatusCode(200);
                resp.setMessage("Programación horario eliminada exitosamente");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Programación horario no encontrada para eliminar");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error ocurrido al eliminar la programación horario: " + e.getMessage());
        }
        return resp;
    }

    public ReqResProgramacionHr listar() {
        ReqResProgramacionHr resp = new ReqResProgramacionHr();

        try {
            List<ProgramacionHorario> programacionesHorario = (List<ProgramacionHorario>) programacionHrRepository.findAll();
            resp.setProgramacionHorarioList(programacionesHorario);
            resp.setMessage("Programaciones horario recuperadas exitosamente");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
        }
        return resp;
    }

    public ReqResProgramacionHr getProgramacionHorarioById(Long programacionHorarioId) {
        ReqResProgramacionHr resp = new ReqResProgramacionHr();
        try {
            Optional<ProgramacionHorario> optionalProgramacionHorario = programacionHrRepository.findById(programacionHorarioId);
            if (optionalProgramacionHorario.isPresent()) {
                ProgramacionHorario programacionHorario = optionalProgramacionHorario.get();
                resp.setProgramacionHorario(programacionHorario);
                resp.setStatusCode(200);
                resp.setMessage("Programación horario con id '" + programacionHorarioId + "' encontrada exitosamente");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Programación horario con id '" + programacionHorarioId + "' no encontrada");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
        }
        return resp;
    }
}
