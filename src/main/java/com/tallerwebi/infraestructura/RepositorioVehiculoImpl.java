package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EstadoVehiculo;
import com.tallerwebi.dominio.Vehiculo;
import org.hibernate.SessionFactory;
import com.tallerwebi.dominio.RepositorioVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public Vehiculo modificar(Vehiculo vehiculoAlmacenado) {
        sessionFactory.getCurrentSession().update(vehiculoAlmacenado);
        return sessionFactory.getCurrentSession().get(Vehiculo.class, vehiculoAlmacenado.getId());
    }

    @Override
    public Vehiculo buscar(long l) {
        return sessionFactory.getCurrentSession().get(Vehiculo.class, l);
    }

    @Override
    public List<Vehiculo> buscarHabilitados() {
        String hql = "FROM Vehiculo l WHERE l.estado = :estadoVehiculo";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Vehiculo.class)
                .setParameter("estadoVehiculo", EstadoVehiculo.Habilitado)
                .getResultList();
    }

    @Override
    public List<Vehiculo> buscarInhabilitados() {
        String hql = "FROM Vehiculo l WHERE l.estado = :estadoVehiculo";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Vehiculo.class)
                .setParameter("estadoVehiculo", EstadoVehiculo.Inhabilitado)
                .getResultList();
    }
}
