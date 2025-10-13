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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {HibernateTestConfig.class})
@Transactional
public class RepositorioSombreroTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioSombrero repositorioSombrero;

    @BeforeEach
    public void init(){
        repositorioSombrero = new RepositorioSombreroImpl(sessionFactory);
    }

    @Test
    @Rollback
    public void testQueAlAgregarUnSombreroALaBaseDeDatosLosDatosSeAlmacenanCorrectamente() {
        Sombrero sombrero = new Sombrero();
        sombrero.setTipo(TipoSombrero.BEANIE);
        sombrero.setId(1L);
        sombrero.setLote(null);

        Sombrero sombrero2 = repositorioSombrero.agregar(sombrero);
        assertNotNull(sombrero2);
        assertEquals(sombrero2.getTipo(), TipoSombrero.BEANIE);
    }

    @Test
    @Rollback
    public void testQueAlAgregarUnSombreroConLoteALaBaseDeDatosLosDatosSeAlmacenanCorrectamente() {
        Sombrero sombrero = new Sombrero();
        sombrero.setTipo(TipoSombrero.BEANIE);
        sombrero.setId(1L);
        sombrero.setLote(null);

        Sombrero sombrero1 = repositorioSombrero.agregar(sombrero);

        Lote lote = new Lote();
        lote.setId(1L);
        lote.setEstado(EstadoLote.No_Iniciado);

        sombrero1.setLote(lote);

        Sombrero sombrero2 = repositorioSombrero.modificar(sombrero1);
        assertNotNull(sombrero2);
        assertEquals(sombrero2.getTipo(), TipoSombrero.BEANIE);
        assertNotNull(sombrero2.getLote());
        assertEquals(sombrero2.getLote(), lote);
    }

}
