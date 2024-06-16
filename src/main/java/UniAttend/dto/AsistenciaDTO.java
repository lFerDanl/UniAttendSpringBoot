package UniAttend.dto;

import UniAttend.entity.Asistencia;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AsistenciaDTO {
    private Long id;
    private LocalDate fecha;
    private String estado;
    private LocalTime hora;
    private UserDTO usuario;
    private ProgramacionHorarioDTO programacionHorario;

    public AsistenciaDTO(){
    }

    public AsistenciaDTO(Asistencia asistencia){
        this.id = asistencia.getId();
        this.estado = asistencia.getEstado();
        this.fecha = asistencia.getFecha();
        this.hora = asistencia.getHora();
        this.usuario = new UserDTO(asistencia.getUsuario());
        this.programacionHorario = new ProgramacionHorarioDTO(asistencia.getProgramacionHorario());
    }
}
