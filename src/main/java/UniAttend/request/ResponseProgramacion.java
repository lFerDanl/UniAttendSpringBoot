package UniAttend.request;

import UniAttend.dto.*;
import UniAttend.entity.Grupo;
import UniAttend.entity.Materia;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseProgramacion {
    private int statusCode;
    private String error;
    private String message;
    private List<Programacion> programacionList;
}
