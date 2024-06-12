package UniAttend.request;

import UniAttend.dto.AulaDTO;
import UniAttend.dto.ModuloDTO;
import UniAttend.entity.Horario;
import UniAttend.entity.ProgramacionHorario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqResProgramacionHr {

    private int statusCode;
    private String error;
    private String message;
    private ProgramacionHorario programacionHorario;
    private List<ProgramacionHorario> programacionHorarioList;
    private Horario horario;
    private ModuloDTO modulo;
    private AulaDTO aula;
}
