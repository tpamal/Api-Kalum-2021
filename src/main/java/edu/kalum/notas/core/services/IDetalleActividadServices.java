package edu.kalum.notas.core.services;

import edu.kalum.notas.core.entities.DetalleActividad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDetalleActividadServices {
    public List<DetalleActividad> findAll();
    public Page<DetalleActividad> findAll (Pageable pegeable);
    public DetalleActividad save(DetalleActividad detalleActividad);
    public DetalleActividad findById(String id);
    public void delete (DetalleActividad detalleActividad);
    public void deleteById(String id);
}
