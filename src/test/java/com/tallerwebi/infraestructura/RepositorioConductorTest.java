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
public class RepositorioConductorTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioConductor repositorioConductor;

    @BeforeEach
    public void setUp() {
        repositorioConductor = new RepositorioConductorImpl(sessionFactory);
    }

    @Test
    @Rollback
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

    @Test
    @Rollback
    public void testQueAlModificarUnConductorLosDatosSeActualicenCorrectamente(){
        Conductor conductor = new Conductor();
        conductor.setNombre("Juan Perez");
        conductor.setId(1L);
        conductor.setEdad(50);
        conductor.setEstado(EstadoConductor.Habilitado);
        conductor.setSexo("Masculino");
        conductor.setCedula("123ABC");

        Conductor conductorAlmacenado = repositorioConductor.crear(conductor);

        conductorAlmacenado.setNombre("Pedro Gomez");
        conductorAlmacenado.setEdad(40);
        conductorAlmacenado.setEstado(EstadoConductor.Inhabilitado);
        conductorAlmacenado.setSexo("Femenino");
        conductorAlmacenado.setCedula("456DEF");

        Conductor conductorModificado = repositorioConductor.modificar(conductorAlmacenado);

        assertNotNull(conductorModificado);
        assertEquals("Pedro Gomez", conductorModificado.getNombre());
        assertEquals(EstadoConductor.Inhabilitado, conductorModificado.getEstado());
        assertEquals(40, conductorModificado.getEdad());
        assertEquals("Femenino", conductorModificado.getSexo());
        assertEquals("456DEF", conductorModificado.getCedula());
    }

    @Test
    @Rollback
    public void testQueMePermitaCambiarElEstadoDeUnConductorDeInhabilitadoAHabilitado(){
        Conductor conductor = new Conductor();
        conductor.setNombre("Juan Perez");
        conductor.setId(1L);
        conductor.setEdad(50);
        conductor.setEstado(EstadoConductor.Inhabilitado);
        conductor.setSexo("Masculino");
        conductor.setCedula("123ABC");

        Conductor conductorAlmacenado = repositorioConductor.crear(conductor);
        assertNotNull(conductorAlmacenado);
        assertEquals("Juan Perez", conductorAlmacenado.getNombre());
        assertEquals(EstadoConductor.Inhabilitado, conductorAlmacenado.getEstado());
        assertEquals(50, conductorAlmacenado.getEdad());
        assertEquals("Masculino", conductorAlmacenado.getSexo());
        assertEquals("123ABC", conductorAlmacenado.getCedula());

        conductorAlmacenado.setEstado(EstadoConductor.Habilitado);
        Conductor conductorModificado = repositorioConductor.modificar(conductorAlmacenado);

        assertNotNull(conductorModificado);
        assertEquals("Juan Perez", conductorModificado.getNombre());
        assertEquals(EstadoConductor.Habilitado, conductorModificado.getEstado());
        assertEquals(50, conductorModificado.getEdad());
        assertEquals("Masculino", conductorModificado.getSexo());
        assertEquals("123ABC", conductorModificado.getCedula());
    }

    @Test
    @Rollback
    public void testQueMePermitaCambiarElEstadoDeUnConductorDeHabilitadoAInhabilitado(){
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

        conductorAlmacenado.setEstado(EstadoConductor.Inhabilitado);
        Conductor conductorModificado = repositorioConductor.modificar(conductorAlmacenado);

        assertNotNull(conductorModificado);
        assertEquals("Juan Perez", conductorModificado.getNombre());
        assertEquals(EstadoConductor.Inhabilitado, conductorModificado.getEstado());
        assertEquals(50, conductorModificado.getEdad());
        assertEquals("Masculino", conductorModificado.getSexo());
        assertEquals("123ABC", conductorModificado.getCedula());
    }

    @Test
    @Rollback
    public void testQueAlBuscarAUnConductorMeDevuelvaLosDatosCorrectos(){
        Conductor conductor = new Conductor();
        conductor.setNombre("Juan Perez");
        conductor.setId(1L);
        conductor.setEdad(50);
        conductor.setEstado(EstadoConductor.Habilitado);
        conductor.setSexo("Masculino");
        conductor.setCedula("123ABC");

        Conductor conductorAlmacenado = repositorioConductor.crear(conductor);

        Conductor conductorBuscado = repositorioConductor.buscar(conductorAlmacenado.getId());

        assertNotNull(conductorBuscado);
        assertEquals(conductorAlmacenado.getId(), conductorBuscado.getId());
        assertEquals(conductorAlmacenado.getNombre(), conductorBuscado.getNombre());
        assertEquals(conductorAlmacenado.getEstado(), conductorBuscado.getEstado());
        assertEquals(conductorAlmacenado.getEdad(), conductorBuscado.getEdad());
        assertEquals(conductorAlmacenado.getSexo(), conductorBuscado.getSexo());
        assertEquals(conductorAlmacenado.getCedula(), conductorBuscado.getCedula());
    }

    @Test
    @Rollback
    public void testQueAlBuscarUnConductorInexistenteMeDevuelvaNull(){
        Conductor conductorBuscado = repositorioConductor.buscar(999L);
        assertEquals(null, conductorBuscado);
    }

    @Test
    @Rollback
    public void testQueSiTengo2ConductoresInhabilitadosYSolicitoLosHabilitadosMeDevuelvaUnaListaVacia(){
        Conductor conductorH1 = new Conductor();
        conductorH1.setNombre("Conductor Habilitado 1");
        conductorH1.setId(1L);
        conductorH1.setEdad(40);
        conductorH1.setSexo("Masculino");
        conductorH1.setCedula("HAB1");
        conductorH1.setEstado(EstadoConductor.Inhabilitado);

        Conductor conductorH2 = new Conductor();
        conductorH2.setNombre("Conductor Habilitado 2");
        conductorH2.setId(2L);
        conductorH2.setEdad(35);
        conductorH2.setSexo("Femenino");
        conductorH2.setCedula("HAB2");
        conductorH2.setEstado(EstadoConductor.Inhabilitado);

        repositorioConductor.crear(conductorH1);
        repositorioConductor.crear(conductorH2);

        List<Conductor> conductoresHabilitados = repositorioConductor.buscarHabilitados();
        assertNotNull(conductoresHabilitados);
        assertEquals(0, conductoresHabilitados.size());
    }


    @Test
    @Rollback
    public void testQueSiTengo2ConductoresHabilitadosYSolicitoLosInhabilitadosMeDevuelvaUnaListaVacia(){
        Conductor conductorH1 = new Conductor();
        conductorH1.setNombre("Conductor Habilitado 1");
        conductorH1.setId(1L);
        conductorH1.setEdad(40);
        conductorH1.setSexo("Masculino");
        conductorH1.setCedula("HAB1");
        conductorH1.setEstado(EstadoConductor.Habilitado);

        Conductor conductorH2 = new Conductor();
        conductorH2.setNombre("Conductor Habilitado 2");
        conductorH2.setId(2L);
        conductorH2.setEdad(35);
        conductorH2.setSexo("Femenino");
        conductorH2.setCedula("HAB2");
        conductorH2.setEstado(EstadoConductor.Habilitado);

        repositorioConductor.crear(conductorH1);
        repositorioConductor.crear(conductorH2);

        List<Conductor> conductoresInhabilitados = repositorioConductor.buscarInhabilitados();
        assertNotNull(conductoresInhabilitados);
        assertEquals(0, conductoresInhabilitados.size());
    }
}
