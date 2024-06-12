package UniAttend.request;


import UniAttend.dto.*;
import UniAttend.entity.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqResProgramacion {

    private int statusCode;
    private String error;
    private String message;
    private ProgramacionDTO programacion;
    private List<ProgramacionDTO> programacionList;
    private Long programacionAcademicaId;
    private Long id;
    private String cupos;
    private Materia materia;
    private Grupo grupo;
    private UserDTO usuario;
    private List<Long> carrerasId;
    private List<CarreraDTO> carreraList;
    private List<ProgramacionHorarioDTO> programacionHorarios;
    private String dia;
}
