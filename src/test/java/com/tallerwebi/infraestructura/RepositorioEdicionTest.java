package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Edicion;
import com.tallerwebi.dominio.RepositorioEdicion;
import com.tallerwebi.dominio.TipoContenido;
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
public class RepositorioEdicionTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioEdicion repositorioEdicion;

    @BeforeEach
    public void init() {
        repositorioEdicion = new RepositorioEdicionImpl(sessionFactory);
    }

    @Test
    @Rollback
    public void testQueMePermitaCrearUnaNuevaEdicionYLosDatosPersistanCorrectamente() {
        Edicion edicion = new Edicion();
        edicion.setNumeroEdicion(1L);
        edicion.setCostoProduccionUnitario(100.0);
        edicion.setTipoContenido(TipoContenido.Mensual);

        Edicion edicionAlmacenada = repositorioEdicion.guardar(edicion);

        assertNotNull(edicionAlmacenada);
        assertEquals(1L, edicionAlmacenada.getNumeroEdicion());
        assertEquals(100.0, edicionAlmacenada.getCostoProduccionUnitario());
        assertEquals(TipoContenido.Mensual, edicionAlmacenada.getTipoContenido());
    }

    @Test
    @Rollback
    public void testQueAlAlmacenar3EdicionesEnLaBaseDeDatosYSolicitarUnaListaDeLasMismasMeRetorneUnaListaCon3Ediciones() {
        Edicion edicion1 = new Edicion();
        edicion1.setNumeroEdicion(1L);
        edicion1.setCostoProduccionUnitario(100.0);
        edicion1.setTipoContenido(TipoContenido.Mensual);
        repositorioEdicion.guardar(edicion1);

        Edicion edicion2 = new Edicion();
        edicion2.setNumeroEdicion(2L);
        edicion2.setCostoProduccionUnitario(150.0);
        edicion2.setTipoContenido(TipoContenido.Anual);
        repositorioEdicion.guardar(edicion2);

        Edicion edicion3 = new Edicion();
        edicion3.setNumeroEdicion(3L);
        edicion3.setCostoProduccionUnitario(200.0);
        edicion3.setTipoContenido(TipoContenido.Especial);
        repositorioEdicion.guardar(edicion3);

        List<Edicion> ediciones = repositorioEdicion.obtenerTodasLasEdiciones();

        assertEquals(3L, ediciones.size());
    }
}
