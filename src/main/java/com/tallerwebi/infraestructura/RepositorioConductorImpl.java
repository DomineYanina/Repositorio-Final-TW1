package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.RepositorioConductor;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioConductor")
public class RepositorioConductorImpl implements RepositorioConductor {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioConductorImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Conductor crear(Conductor conductor) {
        sessionFactory.getCurrentSession().save(conductor);
        return conductor;
    }

    @Override
    public Conductor modificar(Conductor conductor) {
        sessionFactory.getCurrentSession().update(conductor);
        return sessionFactory.getCurrentSession().get(Conductor.class, conductor.getId());
    }

    @Override
    public Conductor buscar(long l) {
        return sessionFactory.getCurrentSession().get(Conductor.class, l);
    }
}
