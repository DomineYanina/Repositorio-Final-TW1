package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
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
public class RepositorioVehiculoTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioVehiculo repositorioVehiculo;

    @BeforeEach
    public void setUp() {
        repositorioVehiculo = new RepositorioVehiculoImpl(sessionFactory);
    }

    @Test
    @Rollback
    public void testQueMePermitaCrearCorrectamenteUnVehiculo() {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setEstado(EstadoVehiculo.Habilitado);
        vehiculo.setId(1L);

        Vehiculo vehiculoAlmacenado = repositorioVehiculo.crear(vehiculo);
        assertNotNull(vehiculoAlmacenado);
        assertEquals("Toyota", vehiculoAlmacenado.getMarca());
        assertEquals(EstadoVehiculo.Habilitado, vehiculoAlmacenado.getEstado());
    }

    @Test
    @Rollback
    public void testQueAlModificarUnVehiculoLosDatosSeActualicenCorrectamente(){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setEstado(EstadoVehiculo.Habilitado);
        vehiculo.setId(1L);

        Vehiculo vehiculoAlmacenado = repositorioVehiculo.crear(vehiculo);

        assertNotNull(vehiculoAlmacenado);
        assertEquals("Toyota", vehiculoAlmacenado.getMarca());
        assertEquals(EstadoVehiculo.Habilitado, vehiculoAlmacenado.getEstado());

        vehiculoAlmacenado.setMarca("Mazda");
        vehiculoAlmacenado.setEstado(EstadoVehiculo.Inhabilitado);

        Vehiculo vehiculoModificado = repositorioVehiculo.modificar(vehiculoAlmacenado);

        assertNotNull(vehiculoModificado);
        assertEquals("Mazda", vehiculoModificado.getMarca());
        assertEquals(EstadoVehiculo.Inhabilitado, vehiculoModificado.getEstado());
        assertEquals(vehiculoAlmacenado.getId(), vehiculoModificado.getId());
    }

    @Test
    @Rollback
    public void testQueMePermitaCambiarElEstadoDeUnVehiculoDeInhabilitadoAHabilitado(){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setEstado(EstadoVehiculo.Inhabilitado);
        vehiculo.setId(1L);

        Vehiculo vehiculoAlmacenado = repositorioVehiculo.crear(vehiculo);
        assertNotNull(vehiculoAlmacenado);
        assertEquals("Toyota", vehiculoAlmacenado.getMarca());
        assertEquals(EstadoVehiculo.Inhabilitado, vehiculoAlmacenado.getEstado());

        vehiculoAlmacenado.setEstado(EstadoVehiculo.Habilitado);

        Vehiculo vehiculoModificado = repositorioVehiculo.modificar(vehiculoAlmacenado);

        assertNotNull(vehiculoModificado);
        assertEquals("Toyota", vehiculoModificado.getMarca());
        assertEquals(EstadoVehiculo.Habilitado, vehiculoModificado.getEstado());
        assertEquals(vehiculoAlmacenado.getId(), vehiculoModificado.getId());
    }

    @Test
    @Rollback
    public void testQueMePermitaCambiarElEstadoDeUnVehiculoDeHabilitadoAInhabilitado(){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setEstado(EstadoVehiculo.Habilitado);
        vehiculo.setId(1L);

        Vehiculo vehiculoAlmacenado = repositorioVehiculo.crear(vehiculo);
        assertNotNull(vehiculoAlmacenado);
        assertEquals("Toyota", vehiculoAlmacenado.getMarca());
        assertEquals(EstadoVehiculo.Habilitado, vehiculoAlmacenado.getEstado());

        vehiculoAlmacenado.setEstado(EstadoVehiculo.Inhabilitado);

        Vehiculo vehiculoModificado = repositorioVehiculo.modificar(vehiculoAlmacenado);

        assertNotNull(vehiculoModificado);
        assertEquals("Toyota", vehiculoModificado.getMarca());
        assertEquals(EstadoVehiculo.Inhabilitado, vehiculoModificado.getEstado());
        assertEquals(vehiculoAlmacenado.getId(), vehiculoModificado.getId());
    }

    @Test
    @Rollback
    public void testQueAlBuscarAUnConductorMeDevuelvaLosDatosCorrectos(){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setEstado(EstadoVehiculo.Habilitado);
        vehiculo.setId(1L);

        Vehiculo vehiculoAlmacenado = repositorioVehiculo.crear(vehiculo);

        Vehiculo vehiculoBuscado = repositorioVehiculo.buscar(vehiculoAlmacenado.getId());

        assertNotNull(vehiculoBuscado);
        assertEquals(vehiculoAlmacenado.getId(), vehiculoBuscado.getId());
        assertEquals(vehiculoAlmacenado.getMarca(), vehiculoBuscado.getMarca());
        assertEquals(vehiculoAlmacenado.getEstado(), vehiculoBuscado.getEstado());
    }

    @Test
    @Rollback
    public void testQueAlBuscarUnVehiculoInexistenteMeDevuelvaNull(){
        Vehiculo vehiculoBuscado = repositorioVehiculo.buscar(999L);
        assertEquals(null, vehiculoBuscado);
    }

    @Test
    @Rollback
    public void testQueAlCrear3VehiculosHabilitadosY1InhabilitadosAlBuscarLosVehiculosHabilitadosMeDevuelva3(){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setEstado(EstadoVehiculo.Habilitado);
        vehiculo.setId(1L);

        Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setMarca("Mazda");
        vehiculo2.setEstado(EstadoVehiculo.Habilitado);
        vehiculo2.setId(2L);

        Vehiculo vehiculo3 = new Vehiculo();
        vehiculo3.setMarca("Ford");
        vehiculo3.setEstado(EstadoVehiculo.Habilitado);
        vehiculo3.setId(3L);

        Vehiculo vehiculo4 = new Vehiculo();
        vehiculo4.setMarca("Chevrolet");
        vehiculo4.setEstado(EstadoVehiculo.Inhabilitado);
        vehiculo4.setId(4L);

        Vehiculo vehiculoAlmacenado1 = repositorioVehiculo.crear(vehiculo);
        Vehiculo vehiculoAlmacenado2 = repositorioVehiculo.crear(vehiculo2);
        Vehiculo vehiculoAlmacenado3 = repositorioVehiculo.crear(vehiculo3);
        Vehiculo vehiculoAlmacenado4 = repositorioVehiculo.crear(vehiculo4);

        List<Vehiculo> vehiculosHabilitados = List.of(vehiculo, vehiculo2, vehiculo3);

        List<Vehiculo> vehiculosHabilitadosBuscados = repositorioVehiculo.buscarHabilitados();

        assertEquals(vehiculosHabilitados.size(), vehiculosHabilitadosBuscados.size());
        assertEquals(vehiculosHabilitados.get(0).getId(), vehiculosHabilitadosBuscados.get(0).getId());
        assertEquals(vehiculosHabilitados.get(1).getId(), vehiculosHabilitadosBuscados.get(1).getId());
        assertEquals(vehiculosHabilitados.get(2).getId(), vehiculosHabilitadosBuscados.get(2).getId());
    }

    @Test
    @Rollback
    public void testQueAlCrear3VehiculosHabilitadosY1InhabilitadosAlBuscarLosVehiculosInhabilitadosMeDevuelva1(){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setEstado(EstadoVehiculo.Habilitado);
        vehiculo.setId(1L);

        Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setMarca("Mazda");
        vehiculo2.setEstado(EstadoVehiculo.Habilitado);
        vehiculo2.setId(2L);

        Vehiculo vehiculo3 = new Vehiculo();
        vehiculo3.setMarca("Ford");
        vehiculo3.setEstado(EstadoVehiculo.Habilitado);
        vehiculo3.setId(3L);

        Vehiculo vehiculo4 = new Vehiculo();
        vehiculo4.setMarca("Chevrolet");
        vehiculo4.setEstado(EstadoVehiculo.Inhabilitado);
        vehiculo4.setId(4L);

        Vehiculo vehiculoAlmacenado1 = repositorioVehiculo.crear(vehiculo);
        Vehiculo vehiculoAlmacenado2 = repositorioVehiculo.crear(vehiculo2);
        Vehiculo vehiculoAlmacenado3 = repositorioVehiculo.crear(vehiculo3);
        Vehiculo vehiculoAlmacenado4 = repositorioVehiculo.crear(vehiculo4);

        List<Vehiculo> vehiculosInhabilitados = List.of(vehiculo4);

        List<Vehiculo> vehiculosInhabilitadosBuscados = repositorioVehiculo.buscarInhabilitados();

        assertEquals(vehiculosInhabilitados.size(), vehiculosInhabilitadosBuscados.size());
        assertEquals(vehiculosInhabilitados.get(0).getId(), vehiculosInhabilitadosBuscados.get(0).getId());
    }

    @Test
    @Rollback
    public void testQueSiTengo2VehiculosInhabilitadosYSolicitoLosHabilitadosMeDevuelvaUnaListaVacia(){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setEstado(EstadoVehiculo.Inhabilitado);
        vehiculo.setId(1L);

        Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setMarca("Mazda");
        vehiculo2.setEstado(EstadoVehiculo.Inhabilitado);
        vehiculo2.setId(2L);

        repositorioVehiculo.crear(vehiculo);
        repositorioVehiculo.crear(vehiculo2);

        List<Vehiculo> vehiculosHabilitados = repositorioVehiculo.buscarHabilitados();
        assertNotNull(vehiculosHabilitados);
        assertEquals(0, vehiculosHabilitados.size());
    }


    @Test
    @Rollback
    public void testQueSiTengo2VehiculosHabilitadosYSolicitoLosHabilitadosMeDevuelvaUnaListaVacia(){
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca("Toyota");
        vehiculo.setEstado(EstadoVehiculo.Habilitado);
        vehiculo.setId(1L);

        Vehiculo vehiculo2 = new Vehiculo();
        vehiculo2.setMarca("Mazda");
        vehiculo2.setEstado(EstadoVehiculo.Habilitado);
        vehiculo2.setId(2L);

        repositorioVehiculo.crear(vehiculo);
        repositorioVehiculo.crear(vehiculo2);

        List<Vehiculo> vehiculosInhabilitados = repositorioVehiculo.buscarInhabilitados();
        assertNotNull(vehiculosInhabilitados);
        assertEquals(0, vehiculosInhabilitados.size());
    }

}
