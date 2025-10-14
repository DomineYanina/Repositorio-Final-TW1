package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Vehiculo;
import org.hibernate.SessionFactory;
import com.tallerwebi.dominio.RepositorioVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioVehiculo")
public class RepositorioVehiculoImpl implements RepositorioVehiculo {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioVehiculoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Vehiculo crear(Vehiculo vehiculo) {
        sessionFactory.getCurrentSession().save(vehiculo);
        return vehiculo;
    }
}
