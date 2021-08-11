package edu.kalum.notas.core.services;

import edu.kalum.notas.core.entities.Clase;
import edu.kalum.notas.core.models.dao.IClaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaseServiceImple implements IClaseService{

    @Autowired
    private  IClaseDao claseDao;

    @Override
    public List<Clase> findAll() {
        return this.claseDao.findAll();
    }

    @Override
    public Page<Clase> findAll(Pageable pageable) {
        return this.claseDao.findAll(pageable);
    }

    @Override
    public Clase save(Clase clase) {
        return this.claseDao.save(clase);
    }

    @Override
    public Clase findById(String id) {
        return this.claseDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Clase clase) {
        this.claseDao.delete(clase);
    }

    @Override
    public void deleteById(String id) {
        this.claseDao.deleteById(id);
    }
}
