package UniAttend.dto;

import UniAttend.entity.Asistencia;
import lombok.Data;

@Data
public class AsistenciaE {
    private String estado;

    public AsistenciaE(){
    }

    public AsistenciaE(Asistencia asistencia){
        this.estado = asistencia.getEstado();
    }
}