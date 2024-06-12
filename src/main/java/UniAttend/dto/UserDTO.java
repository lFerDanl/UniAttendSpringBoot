package UniAttend.dto;

import UniAttend.entity.Aula;
import UniAttend.entity.UserEntity;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String password;
    private String city;
    private String role;
    private String telefono;
    private String direccion;
    private String ci;

    public UserDTO(){
    }

    public UserDTO(UserEntity user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.city = user.getCity();
        this.role = user.getRole();
        this.telefono = user.getTelefono();
        this.direccion = user.getDireccion();
        this.ci = user.getCi();
    }
}
