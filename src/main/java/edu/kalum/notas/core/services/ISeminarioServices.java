package edu.kalum.notas.core.services;

import edu.kalum.notas.core.entities.Seminario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISeminarioServices {
    public List<Seminario> findAll();
    public Page<Seminario> findAll (Pageable pegeable);
    public Seminario save(Seminario seminario);
    public Seminario findById(String id);
    public void delete (Seminario seminario);
    public void deleteById(String id);
}
