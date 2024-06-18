package UniAttend.request;

import UniAttend.dto.AsistenciaDTO;
import UniAttend.dto.AsistenciaE;
import UniAttend.dto.AsistenciaN;
import UniAttend.entity.ProgramacionAcademica;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseAsistencia {
    private int statusCode;
    private String error;
    private String message;
    private AsistenciaE asistencia;
    private List<AsistenciaN> asistenciaList;

}