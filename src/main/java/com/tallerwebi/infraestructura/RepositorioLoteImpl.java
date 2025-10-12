package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioLote;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository("repositorioLote")
public class RepositorioLoteImpl implements RepositorioLote {

    private SessionFactory sessionFactory;

    public RepositorioLoteImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
