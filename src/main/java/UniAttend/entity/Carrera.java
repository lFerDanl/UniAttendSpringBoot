package UniAttend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "carreras")
@Data
public class Carrera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    // Relación con Facultad
    @ManyToOne
    @JoinColumn(name = "facultad_id")
    private Facultad facultad;
}
