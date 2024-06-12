package UniAttend.request;

import UniAttend.dto.AulaDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqResAula {

    private int statusCode;
    private String error;
    private String message;
    private Long id;
    private String nro;
    private String capacidad;
    private AulaDTO aula;
    private List<AulaDTO> aulaList;
}
