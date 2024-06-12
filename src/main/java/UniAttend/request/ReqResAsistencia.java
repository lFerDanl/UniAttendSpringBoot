package UniAttend.request;

import UniAttend.dto.AsistenciaDTO;
import UniAttend.entity.Asistencia;
import UniAttend.entity.ProgramacionAcademica;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqResAsistencia {

    private int statusCode;
    private String error;
    private String message;
    private Long id;
    private String fecha;
    private String estado;
    private AsistenciaDTO asistencia;
    private List<AsistenciaDTO> asistenciaList;
    private ProgramacionAcademica programacionAcademica; // Relación con programación académica

}