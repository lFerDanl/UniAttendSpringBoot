package UniAttend.request;

import UniAttend.entity.Materia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqResMateria {

    private int statusCode;
    private String error;
    private String message;
    private Long id;
    private String nombre;
    private Materia materia;
    private List<Materia> materiaList;

}
