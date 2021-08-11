package edu.kalum.notas.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.fxml.Initializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="clase")
@Entity
public class Clase implements Serializable {
    @Id
    @Column(name = "clase_id")
    private String claseid;
    @Column(name ="ciclo")
    private int ciclo;
    @Column(name = "cupoMaximo")
    private int cupoMaximo;
    @Column(name = "cupoMinimo")
    private int cupoMinimo;
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "clase", fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<AsignacionAlumno> asignacion;
}
