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
public class RepositorioLoteTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioLote repositorioLote;
    private RepositorioSombrero repositorioSombrero;

    @BeforeEach
    public void init(){
        repositorioLote = new RepositorioLoteImpl(sessionFactory);
        repositorioSombrero = new RepositorioSombreroImpl(sessionFactory);
    }

    @Test
    @Rollback
    public void testQueAlAgregarUnLoteALaBaseDeDatosLosDatosSeAlmacenanCorrectamente(){
        Lote lote = new Lote();
        lote.setId(1L);
        lote.setEstado(EstadoLote.No_Iniciado);

        Lote lote2 = repositorioLote.agregar(lote);

        assertNotNull(lote2);
        assertEquals(lote2.getEstado(), EstadoLote.No_Iniciado);
    }

    @Test
    @Rollback
    public void testQueAlQuererCambiarElEstadoDeUnLoteEsteSeActualizaCorrectamente(){
        Lote lote = new Lote();
        lote.setId(1L);
        lote.setEstado(EstadoLote.No_Iniciado);

        Lote lote2 = repositorioLote.agregar(lote);
        assertNotNull(lote2);
        assertEquals(lote2.getEstado(), EstadoLote.No_Iniciado);

        lote2.setEstado(EstadoLote.En_Progreso);
        Lote lote3 = repositorioLote.modificar(lote2);
        assertNotNull(lote3);
        assertEquals(lote3.getEstado(), EstadoLote.En_Progreso);
    }

    @Test
    @Rollback
    public void testQueAlAgregarDosSombrerosAUnLoteSeAgreguenCorrectamente() {
        Lote lote = new Lote();
        lote.setId(1L);
        lote.setEstado(EstadoLote.No_Iniciado);

        Lote lote2 = repositorioLote.agregar(lote);

        Sombrero sombrero = new Sombrero();
        sombrero.setTipo(TipoSombrero.BEANIE);
        sombrero.setId(1L);
        sombrero.setValorProduccion(1.0);
        sombrero.setLote(lote2);

        Sombrero sombrero2 = repositorioSombrero.agregar(sombrero);
        assertNotNull(sombrero2);
        assertEquals(sombrero2.getTipo(), TipoSombrero.BEANIE);

        Sombrero sombrero3 = new Sombrero();
        sombrero3.setTipo(TipoSombrero.CAP);
        sombrero3.setId(2L);
        sombrero3.setValorProduccion(10.0);
        sombrero3.setLote(lote2);

        Sombrero sombrero4 = repositorioSombrero.agregar(sombrero3);
        assertNotNull(sombrero4);
        assertEquals(sombrero4.getTipo(), TipoSombrero.CAP);

        List<Sombrero> sombrerosEnLote = repositorioLote.obtenerSombrerosPorLote(lote2.getId());
        assertEquals(2, sombrerosEnLote.size());
        assertEquals(sombrero2.getId(), sombrerosEnLote.get(0).getId());
        assertEquals(sombrero4.getId(), sombrerosEnLote.get(1).getId());

        Double precioPorLote = sombrerosEnLote.stream().mapToDouble(Sombrero::getValorProduccion).sum();
        assertEquals(11.0, precioPorLote);
    }

    @Test
    @Rollback
    public void queAlFiltrarLosLotesConEstadoFinalizadoSoloMeTraigaLosCorrectos(){
        Lote loteEnProgreso = new Lote();
        loteEnProgreso.setId(1L);
        loteEnProgreso.setEstado(EstadoLote.En_Progreso);

        Lote loteFinalizado1 = new Lote();
        loteFinalizado1.setId(2L);
        loteFinalizado1.setEstado(EstadoLote.Completado);

        Lote loteFinalizado2 = new Lote();
        loteFinalizado2.setId(3L);
        loteFinalizado2.setEstado(EstadoLote.Completado);

        repositorioLote.agregar(loteEnProgreso);
        repositorioLote.agregar(loteFinalizado1);
        repositorioLote.agregar(loteFinalizado2);

        List<Lote> lotesFinalizados = repositorioLote.buscarLotesFinalizados();

        assertNotNull(lotesFinalizados);
        assertEquals(2, lotesFinalizados.size());

    }
}
