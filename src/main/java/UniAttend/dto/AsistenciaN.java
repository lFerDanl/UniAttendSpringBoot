package UniAttend.dto;

import UniAttend.entity.Asistencia;

public class AsistenciaN {
    private Long id;
    private String fecha;
    private String estado;
    private String hora;

    public AsistenciaN(){
    }

    public AsistenciaN(Asistencia asistencia){
        this.id = asistencia.getId();
        this.estado = asistencia.getEstado();
        this.fecha = String.valueOf(asistencia.getFecha());
        this.hora = String.valueOf(asistencia.getHora());
    }
}
