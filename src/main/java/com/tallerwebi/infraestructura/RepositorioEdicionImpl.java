package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Edicion;
import com.tallerwebi.dominio.RepositorioEdicion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioEdicionImpl implements RepositorioEdicion {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioEdicionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Edicion guardar(Edicion edicion) {
        sessionFactory.getCurrentSession().save(edicion);
        return edicion;
    }

    @Override
    public List<Edicion> obtenerTodasLasEdiciones() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Edicion", Edicion.class)
                .getResultList();
    }
}
