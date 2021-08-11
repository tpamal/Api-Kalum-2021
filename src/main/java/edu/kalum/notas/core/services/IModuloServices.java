package edu.kalum.notas.core.services;

import edu.kalum.notas.core.entities.Modulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IModuloServices {
    public List<Modulo> findAll();
    public Page<Modulo> findAll (Pageable pegeable);
    public Modulo save(Modulo modulo);
    public Modulo findById(String id);
    public void delete (Modulo modulo);
    public void deleteById(String id);
}
