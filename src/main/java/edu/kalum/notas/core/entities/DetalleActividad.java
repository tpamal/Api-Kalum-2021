package edu.kalum.notas.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalle_actividad")
public class DetalleActividad {
    @Id
    @Column(name = "detalle_actividad_id")
    private String detalleActividadId;
    @Column(name = "seminario_id")
    private String seminarioId;
    @Column(name = "nombre_actividad")
    private String nombreActividad;
    @Column(name = "nota_actividad")
    private String notaActividad;
    @Column(name = "fecha_creacion")
    private Date fechaCreacion;
    @Column(name = "fecha_entrega")
    private Date fechaEntrega;
    @Column(name = "fecha_postergacion")
    private Date fechaPostergacion;
    @Column(name = "estado")
    private String estado;

}
