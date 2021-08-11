package edu.kalum.notas.core.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name= "alumno")
@Entity
public class Alumno implements Serializable {
    @Id
    @Column(name = "carne")
    @NotEmpty(message = "Es necesario asignar un numero de carne")
    private String carne;
    @Column(name = "noexpediente")
    @NotEmpty(message = "Es necesario asignar un numero de expediente")
    private String noExpediente;
    @NotEmpty(message = "El campo apellidos no puede ser vacio")
    @Column(name = "apellidos")
    private String apellidos;
    @NotEmpty(message = "El campo nombres no puede ser vacio")
    @Column(name = "nombres")
    private String nombres;
    @NotEmpty(message = "Ingrese un correo")
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "alumno", fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<AsignacionAlumno> asignaciones;
}
