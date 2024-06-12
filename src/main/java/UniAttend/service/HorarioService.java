package UniAttend.service;

import UniAttend.dto.CarreraDTO;
import UniAttend.dto.HorarioDTO;
import UniAttend.request.ReqResHorario;
import UniAttend.entity.Horario;
import UniAttend.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    public ReqResHorario guardar(ReqResHorario registrationRequest) {
        ReqResHorario resp = new ReqResHorario();
        try {
            Horario horario = new Horario();
            horario.setDia(registrationRequest.getDia());
            horario.setHorarioInicio(registrationRequest.getHorarioInicio());
            horario.setHorarioFin(registrationRequest.getHorarioFin());
            Horario horarioResult = horarioRepository.save(horario);
            if (horarioResult.getId() != null) {
                resp.setHorario(new HorarioDTO(horarioResult));
                resp.setMessage("Horario Saved Successfully");
                resp.setStatusCode(200);
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }
    public ReqResHorario actualizar(Long id, Horario updatedHorario) {
        ReqResHorario resp = new ReqResHorario();

        try {
            Optional<Horario> optionalHorario = horarioRepository.findById(id);
            if (optionalHorario.isPresent()) {
                Horario existingHorario = optionalHorario.get();
                existingHorario.setDia(updatedHorario.getDia());
                existingHorario.setHorarioInicio(updatedHorario.getHorarioInicio());
                existingHorario.setHorarioFin(updatedHorario.getHorarioFin());

                Horario savedHorario = horarioRepository.save(existingHorario);
                resp.setHorario(new HorarioDTO(savedHorario));
                resp.setStatusCode(200);
                resp.setMessage("Horario updated successfully");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Horario not found for update");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred while updating horario: " + e.getMessage());
        }
        return resp;
    }

    public ReqResHorario eliminar(Long id) {
        ReqResHorario resp = new ReqResHorario();

        try {
            Optional<Horario> optionalHorario = horarioRepository.findById(id);
            if (optionalHorario.isPresent()) {
                horarioRepository.deleteById(id);
                resp.setStatusCode(200);
                resp.setMessage("Horario deleted successfully");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Horario not found for deletion");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred while deleting horario: " + e.getMessage());
        }
        return resp;
    }

    public ReqResHorario listar() {
        ReqResHorario resp = new ReqResHorario();

        try {
            List<Horario> horarios = (List<Horario>) horarioRepository.findAll();
            List<HorarioDTO> horarioDTOs = horarios.stream().map(HorarioDTO::new).collect(Collectors.toList());
            resp.setHorarioList(horarioDTOs);
            resp.setMessage("Horarios Retrieved Successfully");
            resp.setStatusCode(200);
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public ReqResHorario getHorarioById(Long horarioId) {
        ReqResHorario resp = new ReqResHorario();
        try {
            Optional<Horario> optionalHorario = horarioRepository.findById(horarioId);
            if (optionalHorario.isPresent()) {
                Horario horario = optionalHorario.get();
                resp.setHorario(new HorarioDTO(horario));
                resp.setStatusCode(200);
                resp.setMessage("Horario with id '" + horarioId + "' found successfully");
            } else {
                resp.setStatusCode(404);
                resp.setMessage("Horario with id '" + horarioId + "' not found");
            }
        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage("Error occurred: " + e.getMessage());
        }
        return resp;
    }


}
