package edu.kalum.notas.core.services;

import edu.kalum.notas.core.entities.DetalleNota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDetalleNotaServices {
    public List<DetalleNota> findAll();
    public Page<DetalleNota> findAll (Pageable pegeable);
    public DetalleNota save(DetalleNota detalleNota);
    public DetalleNota findById(String id);
    public void delete (DetalleNota detalleNota);
    public void deleteById(String id);
}
