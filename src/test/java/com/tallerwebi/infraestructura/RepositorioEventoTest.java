package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EstadoEvento;
import com.tallerwebi.dominio.Evento;
import com.tallerwebi.dominio.RepositorioEvento;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@Transactional
public class RepositorioEventoTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioEvento repositorioEvento;

    @BeforeEach
    public void setUp() {
        repositorioEvento = new RepositorioEventoImpl(sessionFactory);
    }

    @Test
    @Rollback
    public void testQueMePermitaCrearCorrectamenteUnEvento(){
        Evento evento = new Evento();
        evento.setCodigo("EVT001");
        evento.setEstado(EstadoEvento.EnCurso);

        Evento eventoCreado = repositorioEvento.guardar(evento);

        assertNotNull(eventoCreado.getId());
        assertEquals("EVT001", eventoCreado.getCodigo());
        assertEquals(EstadoEvento.EnCurso, eventoCreado.getEstado());
    }

    @Test
    @Rollback
    public void testQueAlCrearDosEventosYBuscarUnoPorElCodigoMeDevuelvaElEventoCorrecto(){
        Evento evento1 = new Evento();
        evento1.setCodigo("EVT001");
        evento1.setEstado(EstadoEvento.EnCurso);
        Evento e1 = repositorioEvento.guardar(evento1);

        Evento evento2 = new Evento();
        evento2.setCodigo("EVT002");
        evento2.setEstado(EstadoEvento.Finalizado);
        Evento e2 = repositorioEvento.guardar(evento2);

        Evento eventoBuscado = repositorioEvento.buscarPorCodigo("EVT002");

        assertNotNull(eventoBuscado);
        assertEquals(e2.getId(), eventoBuscado.getId());
        assertEquals("EVT002", eventoBuscado.getCodigo());
        assertEquals(EstadoEvento.Finalizado, eventoBuscado.getEstado());
    }

    @Test
    @Rollback
    public void testQueMePermitaModificarCorrectamenteElEstadoDeUnEvento(){
        Evento evento = new Evento();
        evento.setCodigo("EVT003");
        evento.setEstado(EstadoEvento.EnCurso);

        Evento eventoCreado = repositorioEvento.guardar(evento);

        eventoCreado.setEstado(EstadoEvento.Finalizado);
        Evento eventoModificado = repositorioEvento.modificar(eventoCreado);

        assertEquals(EstadoEvento.Finalizado, eventoModificado.getEstado());
        assertEquals("EVT003", eventoModificado.getCodigo());
    }

    @Test
    @Rollback
    public void testQueAlCrear3EventosEnCursoY2FinalizadosYSoliciteLosEventosFinalizadosMeDevuelvaUnaListaCon2Eventos(){
        Evento evento1 = new Evento();
        evento1.setCodigo("EVT001");
        evento1.setEstado(EstadoEvento.EnCurso);
        repositorioEvento.guardar(evento1);

        Evento evento2 = new Evento();
        evento2.setCodigo("EVT002");
        evento2.setEstado(EstadoEvento.EnCurso);
        repositorioEvento.guardar(evento2);

        Evento evento3 = new Evento();
        evento3.setCodigo("EVT003");
        evento3.setEstado(EstadoEvento.EnCurso);
        repositorioEvento.guardar(evento3);

        Evento evento4 = new Evento();
        evento4.setCodigo("EVT004");
        evento4.setEstado(EstadoEvento.Finalizado);
        repositorioEvento.guardar(evento4);

        Evento evento5 = new Evento();
        evento5.setCodigo("EVT005");
        evento5.setEstado(EstadoEvento.Finalizado);
        repositorioEvento.guardar(evento5);

        List<Evento> eventosFinalizados = repositorioEvento.obtenerFinalizados();

        assertNotNull(eventosFinalizados);
        assertEquals(2, eventosFinalizados.size());
    }

    @Test
    @Rollback
    public void testQueAlCrear3EventosEnCursoY2FinalizadosYSoliciteTodosLosEventosMeDevuelvaUnaListaCon5Eventos(){
        Evento evento1 = new Evento();
        evento1.setCodigo("EVT001");
        evento1.setEstado(EstadoEvento.EnCurso);
        repositorioEvento.guardar(evento1);

        Evento evento2 = new Evento();
        evento2.setCodigo("EVT002");
        evento2.setEstado(EstadoEvento.EnCurso);
        repositorioEvento.guardar(evento2);

        Evento evento3 = new Evento();
        evento3.setCodigo("EVT003");
        evento3.setEstado(EstadoEvento.EnCurso);
        repositorioEvento.guardar(evento3);

        Evento evento4 = new Evento();
        evento4.setCodigo("EVT004");
        evento4.setEstado(EstadoEvento.Finalizado);
        repositorioEvento.guardar(evento4);

        Evento evento5 = new Evento();
        evento5.setCodigo("EVT005");
        evento5.setEstado(EstadoEvento.Finalizado);
        repositorioEvento.guardar(evento5);

        List<Evento> eventos = repositorioEvento.obtenerTodos();

        assertNotNull(eventos);
        assertEquals(5, eventos.size());
    }

}
