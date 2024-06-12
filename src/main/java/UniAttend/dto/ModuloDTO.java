package UniAttend.dto;

import UniAttend.entity.Aula;
import UniAttend.entity.Modulo;
import lombok.Data;

@Data
public class ModuloDTO {
    private Long id;
    private String nro;

    public ModuloDTO(){
    }

    public ModuloDTO(Modulo modulo) {
        this.id = modulo.getId();
        this.nro = modulo.getNro();
    }
}
