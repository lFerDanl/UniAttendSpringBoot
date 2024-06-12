package UniAttend.request;

import UniAttend.entity.Facultad;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqResFacultad {

    private int statusCode;
    private String error;
    private String message;
    private Long id;
    private String nombre;
    private Facultad facultad;
    private List<Facultad> facultadList;

}
