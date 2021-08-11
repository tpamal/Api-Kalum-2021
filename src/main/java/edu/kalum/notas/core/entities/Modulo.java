package edu.kalum.notas.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "modulo")
public class Modulo {
    @Id
    @Column(name = "modulo_id")
    private String moduloId;
    @Column(name = "nombre_modulo")
    private String nombreModulo;
    @Column(name = "numero_seminarios")
    private String numeroSeminarios;
}
