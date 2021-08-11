package edu.kalum.notas.core.services;

import edu.kalum.notas.core.entities.Seminario;
import edu.kalum.notas.core.models.dao.ISeminarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeminarioServiceImpl implements ISeminarioServices{

    @Autowired
    private ISeminarioDao seminarioDao;

    @Override
    public List<Seminario> findAll() {
        return this.seminarioDao.findAll();
    }

    @Override
    public Page<Seminario> findAll(Pageable pegeable) {
        return this.seminarioDao.findAll(pegeable);
    }

    @Override
    public Seminario save(Seminario seminario) {
        return this.seminarioDao.save(seminario);
    }

    @Override
    public Seminario findById(String id) {
        return this.seminarioDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Seminario seminario) {
        this.seminarioDao.delete(seminario);
    }

    @Override
    public void deleteById(String id) {
        this.seminarioDao.deleteById(id);
    }
}
