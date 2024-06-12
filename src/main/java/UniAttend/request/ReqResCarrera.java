package UniAttend.request;

import UniAttend.dto.CarreraDTO;
import UniAttend.entity.Carrera;
import UniAttend.entity.Facultad;
import UniAttend.entity.ProgramacionAcademica;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqResCarrera {

    private int statusCode;
    private String error;
    private String message;
    private Long id;
    private String nombre;
    private CarreraDTO carrera;
    private List<CarreraDTO> carreraList;
    private Facultad facultad; // Relación con Facultad

}
