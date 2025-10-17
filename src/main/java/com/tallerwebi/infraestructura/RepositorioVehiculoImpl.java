package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EstadoVehiculo;
import com.tallerwebi.dominio.Vehiculo;
import org.hibernate.SessionFactory;
import com.tallerwebi.dominio.RepositorioVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("repositorioVehiculo")
public class RepositorioVehiculoImpl implements RepositorioVehiculo {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioVehiculoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Vehiculo crear(Vehiculo vehiculo) {
        sessionFactory.getCurrentSession().save(vehiculo);
        return vehiculo;
    }

    @Override
    @Transactional
    public Vehiculo modificar(Vehiculo vehiculoAlmacenado) {
        sessionFactory.getCurrentSession().update(vehiculoAlmacenado);
        return sessionFactory.getCurrentSession().get(Vehiculo.class, vehiculoAlmacenado.getId());
    }

    @Override
    @Transactional
    public Vehiculo buscar(long l) {
        return sessionFactory.getCurrentSession().get(Vehiculo.class, l);
    }

    @Override
    @Transactional
    public List<Vehiculo> buscarHabilitados() {
        String hql = "FROM Vehiculo l WHERE l.estado = :estadoVehiculo";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Vehiculo.class)
                .setParameter("estadoVehiculo", EstadoVehiculo.Habilitado)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Vehiculo> buscarInhabilitados() {
        String hql = "FROM Vehiculo l WHERE l.estado = :estadoVehiculo";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Vehiculo.class)
                .setParameter("estadoVehiculo", EstadoVehiculo.Inhabilitado)
                .getResultList();
    }

    @Override
    @Transactional
    public List<Vehiculo> buscarTodos() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Vehiculo", Vehiculo.class)
                .getResultList();
    }
}
