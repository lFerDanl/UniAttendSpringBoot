package UniAttend.request;

import UniAttend.dto.AulaDTO;
import UniAttend.dto.ModuloDTO;
import UniAttend.entity.Aula;
import UniAttend.entity.Modulo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqResModulo {

    private int statusCode;
    private String error;
    private String message;
    private Long id;
    private String nro;
    private ModuloDTO modulo;
    private List<ModuloDTO> moduloList;
    private List<Long> aulasId;
    private List<AulaDTO> aulas;
}
