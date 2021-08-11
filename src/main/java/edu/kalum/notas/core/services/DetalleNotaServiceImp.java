package edu.kalum.notas.core.services;

import edu.kalum.notas.core.entities.DetalleNota;
import edu.kalum.notas.core.models.dao.IAsignacionAlumnoDao;
import edu.kalum.notas.core.models.dao.IDetalleNotaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleNotaServiceImp implements IDetalleNotaServices {

    @Autowired
    private IDetalleNotaDao detalleNotaDao;

    @Override
    public List<DetalleNota> findAll() {
        return this.detalleNotaDao.findAll();
    }

    @Override
    public Page<DetalleNota> findAll(Pageable pegeable) {
        return this.detalleNotaDao.findAll(pegeable);
    }

    @Override
    public DetalleNota save(DetalleNota detalleNota) {
        return this.detalleNotaDao.save(detalleNota);
    }

    @Override
    public DetalleNota findById(String id) {
        return this.detalleNotaDao.findById(id).orElse(null);
    }

    @Override
    public void delete(DetalleNota detalleNota) {
        this.detalleNotaDao.delete(detalleNota);
    }

    @Override
    public void deleteById(String id) {
        this.detalleNotaDao.deleteById(id);
    }
}
