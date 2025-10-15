package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public List<Conductor> buscarHabilitados() {
        String hql = "FROM Conductor l WHERE l.estado = :estadoConductor";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Conductor.class)
                .setParameter("estadoConductor", EstadoConductor.Habilitado)
                .getResultList();
    }

    @Override
    public List<Conductor> buscarInhabilitados() {
        String hql = "FROM Conductor l WHERE l.estado = :estadoConductor";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Conductor.class)
                .setParameter("estadoConductor", EstadoConductor.Inhabilitado)
                .getResultList();
    }

    @Override
    public List<Conductor> buscarTodos() {
        String hql = "FROM Conductor";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Conductor.class)
                .getResultList();
    }
}
