package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.entities.DetalleActividad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDetalleActividadDao extends JpaRepository<DetalleActividad, String> {
}
