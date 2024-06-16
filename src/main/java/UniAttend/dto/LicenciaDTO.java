package UniAttend.dto;


import UniAttend.entity.Licencia;
import lombok.Data;

@Data
public class LicenciaDTO {
    private Long id;
    private String tipo;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
    private UserDTO usuario;

    public LicenciaDTO(){
    }
    public LicenciaDTO(Licencia licencia){
        this.id = licencia.getId();
        this.tipo = licencia.getTipo();
        this.fechaInicio = licencia.getFechaInicio();
        this.fechaFin = licencia.getFechaFin();
        this.estado = licencia.getEstado();
        this.usuario = new UserDTO(licencia.getUsuario());
    }
}
