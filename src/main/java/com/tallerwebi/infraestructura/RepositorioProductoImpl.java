package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Producto;
import com.tallerwebi.dominio.RepositorioProducto;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioProducto")
public class RepositorioProductoImpl implements RepositorioProducto {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioProductoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Producto guardar(Producto producto) {
        sessionFactory.getCurrentSession().save(producto);
        return producto;
    }

    @Override
    public Producto modificar(Producto productoAlmacenado) {
        sessionFactory.getCurrentSession().update(productoAlmacenado);
        return productoAlmacenado;
    }

    @Override
    public List<Producto> buscarPorCategoria(Categoria categoria) {
        return sessionFactory.getCurrentSession().createCriteria(Producto.class)
                .add(Restrictions.eq("categoria", Categoria.ELECTRONICA))
                .list();
    }

    @Override
    public List<Producto> buscarPorStock(Long stock) {
        return sessionFactory.getCurrentSession().createQuery("from Producto where stock > :stock")
                .setParameter("stock", stock)
                .list();
    }
}
