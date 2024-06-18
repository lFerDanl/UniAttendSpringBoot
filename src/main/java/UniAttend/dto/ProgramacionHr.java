package UniAttend.dto;

import UniAttend.entity.Horario;
import UniAttend.entity.ProgramacionHorario;
import lombok.Data;

@Data
public class ProgramacionHr {
    private Long id;
    private Horario horario;
    private ModuloDTO modulo;
    private AulaDTO aula;

    public ProgramacionHr(){
    }

    public ProgramacionHr(ProgramacionHorario programacionHorario){
        this.id = programacionHorario.getId();
        this.horario = programacionHorario.getHorario();
        this.modulo = new ModuloDTO(programacionHorario.getModulo());
        this.aula = new AulaDTO(programacionHorario.getAula());
    }
}
