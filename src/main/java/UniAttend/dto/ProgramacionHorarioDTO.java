package UniAttend.dto;

import UniAttend.entity.Horario;
import UniAttend.entity.ProgramacionAcademica;
import UniAttend.entity.ProgramacionHorario;
import lombok.Data;

@Data
public class ProgramacionHorarioDTO {
    private Long id;
    private Horario horario;
    private ModuloDTO modulo;
    private AulaDTO aula;
    private ProgramacionDTO programacionAcademica;

    public ProgramacionHorarioDTO(){
    }

    public ProgramacionHorarioDTO(ProgramacionHorario programacionHorario){
        this.id = programacionHorario.getId();
        this.horario = programacionHorario.getHorario();
        this.modulo = new ModuloDTO(programacionHorario.getModulo());
        this.aula = new AulaDTO(programacionHorario.getAula());
        this.programacionAcademica = new ProgramacionDTO(programacionHorario.getProgramacionAcademica());
    }
}
