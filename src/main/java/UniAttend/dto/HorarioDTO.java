package UniAttend.dto;

import UniAttend.entity.Horario;
import lombok.Data;

@Data
public class HorarioDTO {
    private Long id;
    private String dia;
    private String horarioInicio;
    private String horarioFin;

    public HorarioDTO (){
    }

    public HorarioDTO (Horario horario){
        this.id = horario.getId();
        this.dia = horario.getDia();
        this.horarioInicio = horario.getHorarioInicio();
        this.horarioFin = horario.getHorarioFin();
    }

}

