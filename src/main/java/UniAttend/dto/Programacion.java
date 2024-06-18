package UniAttend.dto;

import UniAttend.entity.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Programacion {
    private Long id;
    private String cupos;
    private Materia materia;
    private Grupo grupo;
    private UserDTO usuario;
    private List<CarreraDTO> carreras;
    private List<ProgramacionHr> programacionHorarios;

    public Programacion(){
    }

    public Programacion(ProgramacionAcademica programacion) {
        this.id = programacion.getId();
        this.cupos = programacion.getCupos();
        this.materia = programacion.getMateria();
        this.grupo = programacion.getGrupo();
        this.usuario = new UserDTO(programacion.getUsuario());
        // Inicialización de la lista carreras
        this.carreras = new ArrayList<>();
        for (Carrera carrera : programacion.getCarreras()) {
            this.carreras.add(new CarreraDTO(carrera));
        }
        this.programacionHorarios = new ArrayList<>();
        for (ProgramacionHorario programacionHorario : programacion.getProgramacionHorarios()) {
            this.programacionHorarios.add(new ProgramacionHr(programacionHorario));
        }
    }

    public Programacion(ProgramacionAcademica programacion, boolean bandera) {
        if (bandera){
            this.id = programacion.getId();
            this.cupos = programacion.getCupos();
            this.materia = programacion.getMateria();
            this.grupo = programacion.getGrupo();
            this.usuario = new UserDTO(programacion.getUsuario());
            // Inicialización de la lista carreras
            this.carreras = new ArrayList<>();
            for (Carrera carrera : programacion.getCarreras()) {
                this.carreras.add(new CarreraDTO(carrera));
            }
        }
    }
}
