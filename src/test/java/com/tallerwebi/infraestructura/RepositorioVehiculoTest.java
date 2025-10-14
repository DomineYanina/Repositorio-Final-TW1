package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EstadoVehiculo;
import com.tallerwebi.dominio.RepositorioVehiculo;
import com.tallerwebi.dominio.Vehiculo;
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
public class RepositorioVehiculoTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioVehiculo repositorioVehiculo;

    @BeforeEach
    public void setUp() {
        repositorioVehiculo = new RepositorioVehiculoImpl(sessionFactory);
    }

    @Test
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
}
