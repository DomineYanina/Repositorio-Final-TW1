package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Participante;
import com.tallerwebi.dominio.RepositorioParticipante;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioParticipanteImpl implements RepositorioParticipante {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioParticipanteImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Participante guardar(Participante participante) {
        sessionFactory.getCurrentSession().save(participante);
        return participante;
    }

    @Override
    public Participante buscarPorIdInscripcion(String codigo) {
        return sessionFactory.getCurrentSession()
                .createQuery("FROM Participante WHERE idInscripcion = :codigo", Participante.class)
                .setParameter("codigo", codigo)
                .uniqueResult();
    }

}
