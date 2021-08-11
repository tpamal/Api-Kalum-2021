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
@Table(name = "detalle_nota")
public class DetalleNota {
    @Id
    @Column(name = "detalle_nota_id")
    private String detalleNotaId;
    @Column(name = "detalle_actividad_id")
    private String detalleActividadId;
    @Column(name = "valor_nota")
    private String valorNota;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carne", referencedColumnName = "carne")
    @NotNull(message = "El campo carne no es valido")
    private Alumno alumno;

}
