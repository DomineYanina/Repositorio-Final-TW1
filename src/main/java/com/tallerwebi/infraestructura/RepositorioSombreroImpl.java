package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioSombrero;
import com.tallerwebi.dominio.Sombrero;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository("repositorioSombrero")
public class RepositorioSombreroImpl implements RepositorioSombrero {

    private SessionFactory sessionFactory;

    public RepositorioSombreroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Sombrero agregar(Sombrero sombrero) {
        sessionFactory.getCurrentSession().save(sombrero);
        Sombrero sombrero2 = sessionFactory.getCurrentSession().get(Sombrero.class, sombrero.getId());
        return sombrero2;
    }

    @Override
    public Sombrero modificar(Sombrero sombrero1) {
        sessionFactory.getCurrentSession().update(sombrero1);
        Sombrero sombrero2 = sessionFactory.getCurrentSession().get(Sombrero.class, sombrero1.getId());
        return sombrero2;
    }
}
