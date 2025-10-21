package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CodigoPedidoExistente;
import com.tallerwebi.presentacion.PedidoViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.Id;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServicioPedidoTest {
    @Mock
    private RepositorioPedido repositorioPedido;
    @InjectMocks
    private ServicioPedidoImpl servicioPedido;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQueMePermitaCrearUnPedidoCuyoCodigoAunNoExiste(){
        PedidoViewModel pedidoViewModel = new PedidoViewModel();
        pedidoViewModel.setCodigo("PED123");
        pedidoViewModel.setEstado(EstadoPedido.En_Disenio);

        Pedido pedido = new Pedido();
        pedido.setCodigo(pedidoViewModel.getCodigo());
        pedido.setEstado(pedidoViewModel.getEstado());
        pedido.setId(1L);

        when(repositorioPedido.existeCodigo("PED123")).thenReturn(false);
        when(repositorioPedido.guardar(any(Pedido.class))).thenReturn(pedido);
        PedidoViewModel pedidoViewModel2 = servicioPedido.crearPedido(pedidoViewModel);

        assertNotNull(pedidoViewModel2);
        assertEquals(1L, pedidoViewModel2.getId());
        assertEquals("PED123", pedidoViewModel2.getCodigo());
        assertEquals(EstadoPedido.En_Disenio, pedidoViewModel2.getEstado());
        verify(repositorioPedido, times(1)).existeCodigo("PED123");
        verify(repositorioPedido, times(1)).guardar(any(Pedido.class));
    }

    @Test
    public void testQueAlIntentarCrearUnPedidoConUnCodigoExistenteLanceUnaExcepcion(){
        PedidoViewModel pedidoViewModel = new PedidoViewModel();
        pedidoViewModel.setCodigo("PED123");
        pedidoViewModel.setEstado(EstadoPedido.En_Disenio);

        when(repositorioPedido.existeCodigo("PED123")).thenReturn(true);

        assertThrows(CodigoPedidoExistente.class, () -> {
            servicioPedido.crearPedido(pedidoViewModel);
        });
    }

    @Test
    public void testQueAlCrear2PedidosEnDisenioY1EnFabricacionYSoliciteTodosLosPedidosMeDevuelvaUnaListaCon3Pedidos(){
        Pedido pedido1 = new Pedido();
        pedido1.setId(1L);
        pedido1.setCodigo("PED001");
        pedido1.setEstado(EstadoPedido.En_Disenio);

        Pedido pedido2 = new Pedido();
        pedido2.setId(2L);
        pedido2.setCodigo("PED002");
        pedido2.setEstado(EstadoPedido.En_Disenio);

        Pedido pedido3 = new Pedido();
        pedido3.setId(3L);
        pedido3.setCodigo("PED003");
        pedido3.setEstado(EstadoPedido.En_Fabricacion);

        List<Pedido> pedidos = List.of(pedido1, pedido2, pedido3);

        when(repositorioPedido.obtenerTodos()).thenReturn(pedidos);

        List<Pedido> resultados = servicioPedido.obtenerTodos();

        assertNotNull(resultados);
        assertEquals(3, resultados.size());
        verify(repositorioPedido, times(1)).obtenerTodos();
    }

    @Test
    public void testQueAlCrear2PedidosEntregadosY1EnDisenioYSoliciteTodosLosPedidosEntregadosMeDevuelvaUnaListaCon2Pedidos(){
        Pedido pedido1 = new Pedido();
        pedido1.setId(1L);
        pedido1.setCodigo("PED001");
        pedido1.setEstado(EstadoPedido.Entregado);

        Pedido pedido2 = new Pedido();
        pedido2.setId(2L);
        pedido2.setCodigo("PED002");
        pedido2.setEstado(EstadoPedido.Entregado);

        Pedido pedido3 = new Pedido();
        pedido3.setId(3L);
        pedido3.setCodigo("PED003");
        pedido3.setEstado(EstadoPedido.En_Disenio);

        List<Pedido> pedidos = List.of(pedido1, pedido2);

        when(repositorioPedido.obtenerEntregados()).thenReturn(pedidos);

        List<Pedido> resultados = servicioPedido.obtenerEntregados();

        assertNotNull(resultados);
        assertEquals(2, resultados.size());
        verify(repositorioPedido, times(1)).obtenerEntregados();
    }

    @Test
    public void queAlIntentarAvanzarElEstadoDeUnPedidoEnEstadoNoIniciadoPaseASerEnDisenio(){
        PedidoViewModel pedidoViewModel = new PedidoViewModel();
        pedidoViewModel.setCodigo("PED123");
        pedidoViewModel.setEstado(EstadoPedido.No_Iniciado);

        Pedido pedido = new Pedido();
        pedido.setCodigo(pedidoViewModel.getCodigo());
        pedido.setEstado(pedidoViewModel.getEstado());
        pedido.setId(1L);

        Pedido pedidoObjAct = new Pedido();
        pedidoObjAct.setCodigo(pedidoViewModel.getCodigo());
        pedidoObjAct.setEstado(EstadoPedido.En_Disenio);
        pedidoObjAct.setId(1L);

        when(repositorioPedido.buscarPorCodigo("PED123")).thenReturn(pedido);
        when(repositorioPedido.modificar(any(Pedido.class))).thenReturn(pedidoObjAct);

        PedidoViewModel pedidoActualizado = servicioPedido.avanzarEstadoPedido("PED123");
        assertNotNull(pedidoActualizado);
        assertEquals(EstadoPedido.En_Disenio, pedidoActualizado.getEstado());
        verify(repositorioPedido, times(1)).buscarPorCodigo("PED123");
        verify(repositorioPedido, times(1)).modificar(any(Pedido.class));
    }

    @Test
    public void queAlIntentarAvanzarElEstadoDeUnPedidoEnEstadoEnDisenioPaseASerEn_Fabricacion(){
        PedidoViewModel pedidoViewModel = new PedidoViewModel();
        pedidoViewModel.setCodigo("PED123");
        pedidoViewModel.setEstado(EstadoPedido.En_Disenio);

        Pedido pedido = new Pedido();
        pedido.setCodigo(pedidoViewModel.getCodigo());
        pedido.setEstado(pedidoViewModel.getEstado());
        pedido.setId(1L);

        Pedido pedidoObjAct = new Pedido();
        pedidoObjAct.setCodigo(pedidoViewModel.getCodigo());
        pedidoObjAct.setEstado(EstadoPedido.En_Fabricacion);
        pedidoObjAct.setId(1L);

        when(repositorioPedido.buscarPorCodigo("PED123")).thenReturn(pedido);
        when(repositorioPedido.modificar(any(Pedido.class))).thenReturn(pedidoObjAct);

        PedidoViewModel pedidoActualizado = servicioPedido.avanzarEstadoPedido("PED123");
        assertNotNull(pedidoActualizado);
        assertEquals(EstadoPedido.En_Fabricacion, pedidoActualizado.getEstado());
        verify(repositorioPedido, times(1)).buscarPorCodigo("PED123");
        verify(repositorioPedido, times(1)).modificar(any(Pedido.class));
    }

    @Test
    public void queAlIntentarAvanzarElEstadoDeUnPedidoEnEstadoEn_FabricacionPaseASerEntregado(){
        PedidoViewModel pedidoViewModel = new PedidoViewModel();
        pedidoViewModel.setCodigo("PED123");
        pedidoViewModel.setEstado(EstadoPedido.En_Fabricacion);

        Pedido pedido = new Pedido();
        pedido.setCodigo(pedidoViewModel.getCodigo());
        pedido.setEstado(pedidoViewModel.getEstado());
        pedido.setId(1L);

        Pedido pedidoObjAct = new Pedido();
        pedidoObjAct.setCodigo(pedidoViewModel.getCodigo());
        pedidoObjAct.setEstado(EstadoPedido.Entregado);
        pedidoObjAct.setId(1L);

        when(repositorioPedido.buscarPorCodigo("PED123")).thenReturn(pedido);
        when(repositorioPedido.modificar(any(Pedido.class))).thenReturn(pedidoObjAct);

        PedidoViewModel pedidoActualizado = servicioPedido.avanzarEstadoPedido("PED123");
        assertNotNull(pedidoActualizado);
        assertEquals(EstadoPedido.Entregado, pedidoActualizado.getEstado());
        verify(repositorioPedido, times(1)).buscarPorCodigo("PED123");
        verify(repositorioPedido, times(1)).modificar(any(Pedido.class));
    }

    @Test
    public void queAlIntentarAvanzarElEstadoDeUnPedidoEnEstadoEntregadoElEstadoNoCambie(){
        PedidoViewModel pedidoViewModel = new PedidoViewModel();
        pedidoViewModel.setCodigo("PED123");
        pedidoViewModel.setEstado(EstadoPedido.Entregado);

        Pedido pedido = new Pedido();
        pedido.setCodigo(pedidoViewModel.getCodigo());
        pedido.setEstado(pedidoViewModel.getEstado());
        pedido.setId(1L);

        Pedido pedidoObjAct = new Pedido();
        pedidoObjAct.setCodigo(pedidoViewModel.getCodigo());
        pedidoObjAct.setEstado(EstadoPedido.Entregado);
        pedidoObjAct.setId(1L);

        when(repositorioPedido.buscarPorCodigo("PED123")).thenReturn(pedido);
        when(repositorioPedido.modificar(any(Pedido.class))).thenReturn(pedidoObjAct);

        PedidoViewModel pedidoActualizado = servicioPedido.avanzarEstadoPedido("PED123");
        assertNotNull(pedidoActualizado);
        assertEquals(EstadoPedido.Entregado, pedidoActualizado.getEstado());
        verify(repositorioPedido, times(1)).buscarPorCodigo("PED123");
        verify(repositorioPedido, times(1)).modificar(any(Pedido.class));
    }
}
