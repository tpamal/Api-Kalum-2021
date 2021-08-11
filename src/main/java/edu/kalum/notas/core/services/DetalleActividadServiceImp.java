package edu.kalum.notas.core.services;

import edu.kalum.notas.core.entities.DetalleActividad;
import edu.kalum.notas.core.models.dao.IDetalleActividadDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleActividadServiceImp implements IDetalleActividadServices{

    @Autowired
    private IDetalleActividadDao detalleActividadDao;

    @Override
    public List<DetalleActividad> findAll() {
        return this.detalleActividadDao.findAll();
    }

    @Override
    public Page<DetalleActividad> findAll(Pageable pegeable) {
        return this.detalleActividadDao.findAll(pegeable);
    }

    @Override
    public DetalleActividad save(DetalleActividad detalleActividad) {
        return this.detalleActividadDao.save(detalleActividad);
    }

    @Override
    public DetalleActividad findById(String id) {
        return this.detalleActividadDao.findById(id).orElse(null);
    }

    @Override
    public void delete(DetalleActividad detalleActividad) {
        this.detalleActividadDao.delete(detalleActividad);
    }

    @Override
    public void deleteById(String id) {
        this.detalleActividadDao.deleteById(id);
    }
}
