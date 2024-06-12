package UniAttend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Table(name = "programacion_academicas")
@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProgramacionAcademica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cupos;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UserEntity usuario;

    @ManyToMany
    @JoinTable(
            name = "programacion_academica_carrera",
            joinColumns = @JoinColumn(name = "programacion_academica_id"),
            inverseJoinColumns = @JoinColumn(name = "carrera_id")
    )
    private List<Carrera> carreras = new ArrayList<>();

    // Relación de uno a muchos con ProgramacionHorario
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programacionAcademica")
    private List<ProgramacionHorario> programacionHorarios = new ArrayList<>();

}
