package UniAttend.request;

import UniAttend.entity.Grupo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqResGrupo {

    private int statusCode;
    private String error;
    private String message;
    private Long id;
    private String nombre;
    private Grupo grupo;
    private List<Grupo> grupoList;

}
