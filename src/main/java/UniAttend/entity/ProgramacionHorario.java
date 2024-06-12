package UniAttend.entity;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "programacion_horarios")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ProgramacionHorario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación de uno a muchos con Horario
    @ManyToOne
    @JoinColumn(name = "horario_id", referencedColumnName = "id")
    private Horario horario;

    // Relación de uno a muchos con Modulo
    @ManyToOne
    @JoinColumn(name = "modulo_id", referencedColumnName = "id")
    private Modulo modulo;

    // Relación de uno a uno con Aula
    @ManyToOne
    @JoinColumn(name = "aula_id", referencedColumnName = "id")
    private Aula aula;

    // Relación bidireccional: Many-to-One con ProgramacionAcademica
    @ManyToOne
    @JoinColumn(name = "programacion_academica_id")
    private ProgramacionAcademica programacionAcademica;

    // Relación de uno a muchos con Asistencia
    @OneToMany(mappedBy = "programacionHorario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Asistencia> asistencias;
}
