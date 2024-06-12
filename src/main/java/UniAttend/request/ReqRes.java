package UniAttend.request;

import UniAttend.dto.UserDTO;
import UniAttend.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReqRes {

    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String city;
    private String role;
    private String email;
    private String password;
    private String telefono;
    private String direccion;
    private String ci;
    private UserDTO ourUsers;
    private List<UserDTO> ourUsersList;

}
