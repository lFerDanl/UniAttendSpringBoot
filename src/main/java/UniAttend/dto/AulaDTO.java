package UniAttend.dto;

import UniAttend.entity.Aula;
import lombok.Data;

@Data
public class AulaDTO {
    private Long id;
    private String nro;
    private String capacidad;

    public AulaDTO() {
    }

    public AulaDTO(Aula aula) {
        this.id = aula.getId();
        this.nro = aula.getNro();
        this.capacidad = aula.getCapacidad();
    }
}
