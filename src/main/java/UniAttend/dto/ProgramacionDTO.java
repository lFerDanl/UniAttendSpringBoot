package UniAttend.dto;

import UniAttend.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class ProgramacionDTO {
    private Long id;
    private String cupos;
    private Materia materia;
    private Grupo grupo;
    private UserDTO usuario;
    private List<Long> carrerasId;

    public ProgramacionDTO(){
    }

    public ProgramacionDTO(ProgramacionAcademica programacion) {
        this.id = programacion.getId();
        this.cupos = programacion.getCupos();
        this.materia = programacion.getMateria();
        this.grupo = programacion.getGrupo();
        this.usuario = new UserDTO(programacion.getUsuario());
    }

    public ProgramacionDTO(ProgramacionAcademica programacion, List<Long> carrerasId) {
        this.id = programacion.getId();
        this.cupos = programacion.getCupos();
        this.materia = programacion.getMateria();
        this.grupo = programacion.getGrupo();
        this.usuario = new UserDTO(programacion.getUsuario());
        this.carrerasId = carrerasId;
    }
}
