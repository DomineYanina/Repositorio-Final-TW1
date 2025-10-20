package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EstadoPublicacion;
import com.tallerwebi.dominio.Publicacion;
import com.tallerwebi.dominio.RepositorioPublicacion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RepositorioPublicacionImpl implements RepositorioPublicacion {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPublicacionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public Publicacion guardar(Publicacion publicacion) {
        sessionFactory.getCurrentSession().save(publicacion);
        return publicacion;
    }

    @Override
    @Transactional
    public List<Publicacion> obtenerTodasLasPublicaciones() {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Publicacion", Publicacion.class)
                .getResultList();
    }

    @Override
    public Publicacion modificar(Publicacion publicacionAlmacenada) {
        sessionFactory.getCurrentSession().update(publicacionAlmacenada);
        return publicacionAlmacenada;
    }

    @Override
    public List<Publicacion> obtenerTodasLasPublicacionesArchivadas() {
        String hql = "FROM Publicacion l WHERE l.estado = :estadoPublicacion";

        return sessionFactory.getCurrentSession()
                .createQuery(hql, Publicacion.class)
                .setParameter("estadoPublicacion", EstadoPublicacion.ARCHIVADA)
                .getResultList();
    }

    @Override
    public boolean codigoExistente(long codigo) {
        return sessionFactory.getCurrentSession()
                .createQuery("SELECT 1 FROM Publicacion p WHERE p.codigo = :codigo")
                .setParameter("codigo", codigo)
                .uniqueResult() != null;
    }

    @Override
    public Publicacion buscarPorCodigo(long codigo) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Publicacion p WHERE p.codigo = :codigo", Publicacion.class)
                .setParameter("codigo", codigo)
                .uniqueResult();
    }
}
