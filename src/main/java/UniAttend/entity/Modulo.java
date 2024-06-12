package UniAttend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "modulos")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Modulo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nro;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "aula_modulo",
            joinColumns = @JoinColumn(name = "modulo_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "aula_id", referencedColumnName = "id")
    )
    private List<Aula> aulas;

}
