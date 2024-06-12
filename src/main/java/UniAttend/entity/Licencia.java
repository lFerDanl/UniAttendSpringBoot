package UniAttend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "licencias")
@Data
public class Licencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tipo;
    private String fechaInicio;
    private String fechaFin;
    private String estado;


    // Relación con programación académica
    @ManyToOne
    @JoinColumn(name = "programacion_academica_id")
    private ProgramacionAcademica programacionAcademica;

    // Relación con usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UserEntity usuario;

}
