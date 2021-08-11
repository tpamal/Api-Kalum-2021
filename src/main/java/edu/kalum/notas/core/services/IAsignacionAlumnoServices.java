package edu.kalum.notas.core.services;

import edu.kalum.notas.core.entities.AsignacionAlumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAsignacionAlumnoServices {
    public List<AsignacionAlumno> findAll();
    public Page<AsignacionAlumno> findAll (Pageable pegeable);
    public AsignacionAlumno save(AsignacionAlumno asignacionAlumno);
    public AsignacionAlumno findById(String id);
    public void delete (AsignacionAlumno asignacionAlumno);
    public void deleteById(String id);
}
