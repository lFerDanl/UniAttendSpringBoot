package UniAttend.dto;

import UniAttend.entity.Carrera;
import UniAttend.entity.Facultad;
import lombok.Data;

@Data
public class CarreraDTO {
    private Long id;
    private String nombre;
    private Facultad facultad;

    public CarreraDTO(){
    }

    public CarreraDTO(Carrera carrera) {
        this.id = carrera.getId();
        this.nombre = carrera.getNombre();
        this.facultad = carrera.getFacultad();
    }
}
