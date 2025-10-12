package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioSombrero;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository("repositorioSombrero")
public class RepositorioSombreroImpl implements RepositorioSombrero {

    private SessionFactory sessionFactory;

    public RepositorioSombreroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
