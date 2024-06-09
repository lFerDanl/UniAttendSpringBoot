package UniAttend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "horarios")
@Data
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dia;
    private String horarioInicio;
    private String horarioFin;
}
