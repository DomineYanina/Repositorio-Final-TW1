package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Participante;
import com.tallerwebi.dominio.RepositorioParticipante;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@Transactional
public class RepositorioParticipanteTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioParticipante repositorioParticipante;

    @BeforeEach
    public void setUp() {
        repositorioParticipante = new RepositorioParticipanteImpl(sessionFactory);
    }

    @Test
    @Rollback
    public void testQueMePermitaAlmacenarCorrectamenteUnParticipanteEnLaBaseDeDatos() {
        Participante participante = new Participante();
        participante.setIdInscripcion("12345");
        participante.setValorCuota(150.0);

        Participante participanteAlmacenado = repositorioParticipante.guardar(participante);

        assertNotNull(participanteAlmacenado.getId());
        assertEquals("12345", participanteAlmacenado.getIdInscripcion());
        assertEquals(150.0, participanteAlmacenado.getValorCuota());
    }

    @Test
    @Rollback
    public void testQueAlAlmacenar2ParticipantesYBuscarUnoPorCodigoMeDevuelvaElParticipanteCorrecto(){
        Participante participante1 = new Participante();
        participante1.setIdInscripcion("11111");
        participante1.setValorCuota(100.0);
        Participante p1 = repositorioParticipante.guardar(participante1);

        Participante participante2 = new Participante();
        participante2.setIdInscripcion("22222");
        participante2.setValorCuota(200.0);
        Participante p2 = repositorioParticipante.guardar(participante2);

        Participante participanteBuscado = repositorioParticipante.buscarPorIdInscripcion("22222");

        assertNotNull(participanteBuscado);
        assertEquals(p2.getId(), participanteBuscado.getId());
        assertEquals("22222", participanteBuscado.getIdInscripcion());
        assertEquals(200.0, participanteBuscado.getValorCuota());
    }

}