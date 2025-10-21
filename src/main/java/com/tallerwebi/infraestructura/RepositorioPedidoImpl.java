package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EstadoPedido;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.RepositorioPedido;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioPedidoImpl implements RepositorioPedido {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPedidoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Pedido guardar(Pedido pedido) {
        sessionFactory.getCurrentSession().save(pedido);
        return pedido;
    }

    @Override
    public List<Pedido> obtenerTodos() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Pedido", Pedido.class)
                .getResultList();
    }

    @Override
    public List<Pedido> obtenerEntregados() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Pedido WHERE estado = :estado", Pedido.class)
                .setParameter("estado", EstadoPedido.Entregado)
                .getResultList();
    }

    @Override
    public Pedido modificar(Pedido pedido) {
        sessionFactory.getCurrentSession().update(pedido);
        return pedido;
    }

    @Override
    public boolean existeCodigo(String ped123) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT COUNT(p) FROM Pedido p WHERE p.codigo = :codigo", Long.class)
                .setParameter("codigo", ped123)
                .uniqueResult() > 0;
    }

    @Override
    public Pedido buscarPorCodigo(String ped123) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Pedido WHERE codigo = :codigo", Pedido.class)
                .setParameter("codigo", ped123)
                .uniqueResult();
    }

    @Override
    public Pedido buscarPorId(Long pedido) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Pedido WHERE id = :id", Pedido.class)
                .setParameter("id", pedido)
                .uniqueResult();
    }
}
