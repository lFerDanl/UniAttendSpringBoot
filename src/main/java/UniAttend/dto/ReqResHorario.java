package UniAttend.dto;

import UniAttend.entity.Horario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqResHorario {

    private int statusCode;
    private String error;
    private String message;
    private Long id;
    private String dia;
    private String horarioInicio;
    private String horarioFin;
    private Horario horario;
    private List<Horario> horarioList;
}
