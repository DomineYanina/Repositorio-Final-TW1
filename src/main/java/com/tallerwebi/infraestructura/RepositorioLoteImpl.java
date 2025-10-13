package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EstadoLote;
import com.tallerwebi.dominio.Lote;
import com.tallerwebi.dominio.RepositorioLote;
import com.tallerwebi.dominio.Sombrero;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioLote")
public class RepositorioLoteImpl implements RepositorioLote {

    private final SessionFactory sessionFactory;

    public RepositorioLoteImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Lote agregar(Lote lote) {
        sessionFactory.getCurrentSession().save(lote);
        return sessionFactory.getCurrentSession().get(Lote.class, lote.getId());
    }

    @Override
    public Lote modificar(Lote lote2) {
        sessionFactory.getCurrentSession().update(lote2);
        return sessionFactory.getCurrentSession().get(Lote.class, lote2.getId());
    }

    @Override
    public List<Sombrero> obtenerSombrerosPorLote(Long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Sombrero WHERE lote.id = :loteId", Sombrero.class)
                .setParameter("loteId", id)
                .getResultList();
    }

    @Override
    public Lote buscarPorId(Long id) {
        return sessionFactory.getCurrentSession().get(Lote.class, id);
    }

    @Override
    public List<Lote> buscarLotesFinalizados() {
        String hql = "FROM Lote l WHERE l.estado = :estadoLote";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Lote.class)
                .setParameter("estadoLote", EstadoLote.Completado)
                .getResultList();
    }

    @Override
    public List<Lote> obtenerTodosLosLotes() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Lote", Lote.class)
                .getResultList();
    }
}
