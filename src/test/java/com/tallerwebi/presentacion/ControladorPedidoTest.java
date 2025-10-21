package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoPedido;
import com.tallerwebi.dominio.Mueble;
import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.ServicioPedido;
import com.tallerwebi.dominio.excepcion.CodigoPedidoExistente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ControladorPedidoTest {
    @Mock
    private ServicioPedido servicioPedido;

    @InjectMocks
    private ControladorPedido controladorPedido;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQueAlSolicitarIrACrearUnNuevoPedidoMeRedirijaCorrectamenteACrearPedido(){
        ModelAndView modeloVista = controladorPedido.irACrearUnNuevoPedido();
        assertEquals("crearPedido",modeloVista.getViewName());
        assertThat(modeloVista.getModel(), hasKey("pedido"));
    }

    @Test
    public void testQueAlSolicitarCrearUnNuevoPedidoMeRedirijaCorrectamenteAlListadoDePedidos(){
        PedidoViewModel pedidoViewModelSalida = new PedidoViewModel();
        pedidoViewModelSalida.setCodigo("PED1234");
        pedidoViewModelSalida.setEstado(EstadoPedido.No_Iniciado);
        pedidoViewModelSalida.setId(1L);

        PedidoViewModel pedidoViewModelEntrada = new PedidoViewModel();
        pedidoViewModelEntrada.setCodigo("PED1234");

        Pedido pedido = new Pedido();
        pedido.setCodigo(pedidoViewModelSalida.getCodigo());
        pedido.setEstado(EstadoPedido.No_Iniciado);
        pedido.setId(1L);

        List<Pedido> listaPedidos = List.of(pedido);
        List<Pedido> listaPedidosEntregados = new ArrayList<>();

        when(servicioPedido.crearPedido(any(PedidoViewModel.class))).thenReturn(pedidoViewModelSalida);
        when(servicioPedido.obtenerTodos() ).thenReturn(listaPedidos);
        when(servicioPedido.obtenerEntregados() ).thenReturn(listaPedidosEntregados);

        ModelAndView modeloVista = controladorPedido.crearPedido(pedidoViewModelEntrada);
        List<Pedido> pedidosObtenidos = (List<Pedido>) modeloVista.getModel().get("pedidos");
        List<Pedido> pedidosEntregadosObtenidos = (List<Pedido>) modeloVista.getModel().get("pedidosEntregados");

        assertNotNull(modeloVista);
        assertEquals("listadoPedidos", modeloVista.getViewName());
        assertThat(modeloVista.getModel(), hasKey("pedidos"));
        assertThat(modeloVista.getModel(), hasKey("pedidosEntregados"));
        assertEquals(1, pedidosObtenidos.size());
        assertEquals(0, pedidosEntregadosObtenidos.size());
    }

    @Test
    public void testQueAlSolicitarCrearUnNuevoPedidoCuandoElCodigoYaExisteNoMeRedirijaALaSiguienteVistaYSeAgregueUnError(){
        PedidoViewModel pedidoViewModelSalida = new PedidoViewModel();
        pedidoViewModelSalida.setCodigo("PED1234");
        pedidoViewModelSalida.setEstado(EstadoPedido.No_Iniciado);
        pedidoViewModelSalida.setId(1L);

        PedidoViewModel pedidoViewModelEntrada = new PedidoViewModel();
        pedidoViewModelEntrada.setCodigo("PED1234");

        Pedido pedido = new Pedido();
        pedido.setCodigo(pedidoViewModelSalida.getCodigo());
        pedido.setEstado(EstadoPedido.No_Iniciado);
        pedido.setId(1L);

        List<Pedido> listaPedidos = List.of(pedido);
        List<Pedido> listaPedidosEntregados = new ArrayList<>();

        when(servicioPedido.crearPedido(any(PedidoViewModel.class))).thenThrow(new CodigoPedidoExistente());

        ModelAndView modeloVista = controladorPedido.crearPedido(pedidoViewModelEntrada);
        List<Pedido> pedidosObtenidos = (List<Pedido>) modeloVista.getModel().get("pedidos");
        List<Pedido> pedidosEntregadosObtenidos = (List<Pedido>) modeloVista.getModel().get("pedidosEntregados");

        assertNotNull(modeloVista);
        assertEquals("crearPedido", modeloVista.getViewName());
        assertThat(modeloVista.getModel(), hasKey("pedido"));
        assertThat(modeloVista.getModel(), hasKey("error"));
        assertEquals("Código de pedido existente en otro registro", modeloVista.getModel().get("error"));
    }

    @Test
    public void testQueAlSolicitarIrAAgregarUnNuevoMuebleAUnPedidoMeRedirijaCorrectamenteAAgregarMuebleAPedido(){
        ModelAndView modeloVista = controladorPedido.irAAgregarUnNuevoMuebleAPedido(1L);
        assertEquals("agregarMuebleAPedido",modeloVista.getViewName());
        assertThat(modeloVista.getModel(), hasKey("muebleViewModel"));
    }

    @Test
    public void queLuegoDeAgregarUnMuebleAPedidoMeRedirijaCorrectamenteALaVistaEsperada() {
        PedidoViewModel pedidoViewModel = new PedidoViewModel();
        pedidoViewModel.setId(1L);
        pedidoViewModel.setCodigo("PED1234");
        pedidoViewModel.setEstado(EstadoPedido.No_Iniciado);

        MuebleViewModel muebleViewModel = new MuebleViewModel();
        muebleViewModel.setCodigo("MUE1234");
        muebleViewModel.setCosto(150.0);

        int cantidadMuebles = 3;

        Mueble mueble = new Mueble();
        mueble.setCodigo(muebleViewModel.getCodigo());
        mueble.setCosto(muebleViewModel.getCosto());

        Pedido pedido = new Pedido();
        pedido.setId(pedidoViewModel.getId());
        pedido.setCodigo(pedidoViewModel.getCodigo());
        pedido.setEstado(pedidoViewModel.getEstado());

        pedido.agregarMueble(mueble);
        pedido.agregarMueble(mueble);
        pedido.agregarMueble(mueble);

        when(servicioPedido.buscarPedidoPorId(1L)).thenReturn(pedidoViewModel);
        when(servicioPedido.agregarMuebleAPedido(pedidoViewModel, muebleViewModel, cantidadMuebles)).thenReturn(pedido);
        when(servicioPedido.obtenerTodos()).thenReturn(List.of(pedido));
        when(servicioPedido.obtenerEntregados()).thenReturn(new ArrayList<>());

        ModelAndView modeloVista = controladorPedido.agregarMuebleAPedido(muebleViewModel, cantidadMuebles);

        List<Pedido> pedidosObtenidos = (List<Pedido>) modeloVista.getModel().get("pedidos");
        List<Pedido> pedidosEntregadosObtenidos = (List<Pedido>) modeloVista.getModel().get("pedidosEntregados");

        assertNotNull(modeloVista);
        assertEquals("listadoPedidos", modeloVista.getViewName());
        assertThat(modeloVista.getModel(), hasKey("pedidos"));
        assertThat(modeloVista.getModel(), hasKey("pedidosEntregados"));
        assertEquals(1, pedidosObtenidos.size());
        assertEquals(0, pedidosEntregadosObtenidos.size());
        assertEquals(3, pedidosObtenidos.get(0).getMuebles().size());

    }
}
