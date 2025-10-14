package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Conductor;
import com.tallerwebi.dominio.EstadoConductor;
import com.tallerwebi.dominio.RepositorioConductor;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@Transactional
public class RepositorioConductorTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioConductor repositorioConductor;

    @BeforeEach
    public void setUp() {
        repositorioConductor = new RepositorioConductorImpl(sessionFactory);
    }

    @Test
    public void testQueMePermitaCrearCorrectamenteUnConductor() {
        Conductor conductor = new Conductor();
        conductor.setNombre("Juan Perez");
        conductor.setId(1L);
        conductor.setEdad(50);
        conductor.setEstado(EstadoConductor.Habilitado);
        conductor.setSexo("Masculino");
        conductor.setCedula("123ABC");

        Conductor conductorAlmacenado = repositorioConductor.crear(conductor);

        assertNotNull(conductorAlmacenado);
        assertEquals("Juan Perez", conductorAlmacenado.getNombre());
        assertEquals(EstadoConductor.Habilitado, conductorAlmacenado.getEstado());
        assertEquals(50, conductorAlmacenado.getEdad());
        assertEquals("Masculino", conductorAlmacenado.getSexo());
        assertEquals("123ABC", conductorAlmacenado.getCedula());
    }
}
