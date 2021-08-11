package edu.kalum.notas.core.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seminario")
public class Seminario {
    @Id
    @Column(name = "seminario_id")
    private String seminarioId;
    @Column(name = "modulo_id")
    private String moduloId;
    @Column(name = "nombre_seminario")
    private String nombreSeminario;
    @Column(name = "fecha_inicio")
    private Date fechainicio;
    @Column(name = "fecha_final")
    private Date fechaFinal;
}
