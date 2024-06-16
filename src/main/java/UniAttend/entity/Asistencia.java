package UniAttend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "asistencias")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Asistencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private String estado;
    private LocalTime hora;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UserEntity usuario;

    @ManyToOne
    @JoinColumn(name = "programacion_horario_id")
    private ProgramacionHorario programacionHorario;
}
