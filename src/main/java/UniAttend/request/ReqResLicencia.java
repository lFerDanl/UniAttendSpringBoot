package UniAttend.request;

import UniAttend.dto.LicenciaDTO;
import UniAttend.entity.Licencia;
import UniAttend.entity.ProgramacionAcademica;
import UniAttend.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqResLicencia {

    private int statusCode;
    private String error;
    private String message;
    private Long id;
    private String tipo;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
    private LicenciaDTO licencia;
    private List<LicenciaDTO> licenciaList;
}
