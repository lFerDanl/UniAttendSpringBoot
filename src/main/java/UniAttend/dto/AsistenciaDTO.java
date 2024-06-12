package UniAttend.dto;

import UniAttend.entity.Asistencia;

import java.time.LocalDate;

public class AsistenciaDTO {
    private Long id;
    private LocalDate fecha;
    private String estado;

    public AsistenciaDTO(){
    }

    public AsistenciaDTO(Asistencia asistencia){
        this.id = asistencia.getId();
        this.estado = asistencia.getEstado();
        this.fecha = asistencia.getFecha();
    }
}
