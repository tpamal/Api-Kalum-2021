package edu.kalum.notas.core.services;

import edu.kalum.notas.core.entities.AsignacionAlumno;
import edu.kalum.notas.core.models.dao.IAsignacionAlumnoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AsignacionAlumnoServiceImpl implements IAsignacionAlumnoServices{

    @Autowired
    private IAsignacionAlumnoDao asignacionAlumnoDao;

    @Override
    public List<AsignacionAlumno> findAll() {
        return this.asignacionAlumnoDao.findAll();
    }

    @Override
    public Page<AsignacionAlumno> findAll(Pageable pegeable) {
        return this.asignacionAlumnoDao.findAll(pegeable);
    }

    @Override
    public AsignacionAlumno save(AsignacionAlumno asignacionAlumno) {
        return this.asignacionAlumnoDao.save(asignacionAlumno);
    }

    @Override
    public AsignacionAlumno findById(String id) {
        return this.asignacionAlumnoDao.findById(id).orElse(null);
    }

    @Override
    public void delete(AsignacionAlumno asignacionAlumno) {
        this.asignacionAlumnoDao.delete(asignacionAlumno);
    }

    @Override
    public void deleteById(String id) {
        this.asignacionAlumnoDao.deleteById(id);
    }
}
