package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EstadoEvento;
import com.tallerwebi.dominio.Evento;
import com.tallerwebi.dominio.RepositorioEvento;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioEventoImpl implements RepositorioEvento {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioEventoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Evento guardar(Evento evento) {
        sessionFactory.getCurrentSession().save(evento);
        return evento;
    }

    @Override
    public Evento buscarPorCodigo(String codigo) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Evento WHERE codigo = :codigo", Evento.class)
                .setParameter("codigo", codigo)
                .uniqueResult();
    }

    @Override
    public Evento modificar(Evento eventoCreado) {
        sessionFactory.getCurrentSession().update(eventoCreado);
        return eventoCreado;
    }

    @Override
    public List<Evento> obtenerFinalizados() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Evento WHERE estado = :estado", Evento.class)
                .setParameter("estado", EstadoEvento.Finalizado)
                .list();
    }

    @Override
    public List<Evento> obtenerTodos() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Evento", Evento.class)
                .list();
    }

    @Override
    public boolean existeCodigo(String codigo) {
        return buscarPorCodigo(codigo) != null;
    }
}
