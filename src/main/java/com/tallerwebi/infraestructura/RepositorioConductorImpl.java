package com.tallerwebi.infraestructura;

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
}
