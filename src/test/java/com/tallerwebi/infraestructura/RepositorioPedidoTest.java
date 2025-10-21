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
public class RepositorioPedidoTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioPedido repositorioPedido;

    @BeforeEach
    public void setUp() {
        repositorioPedido = new RepositorioPedidoImpl(sessionFactory);
    }

    @Test
    @Rollback
    public void testQueMePermitaCrearCorrectamenteUnPedido() {
        Pedido pedido = new Pedido();
        pedido.setCodigo("PED123");
        pedido.setEstado(EstadoPedido.En_Disenio);

        Pedido pedidoAlmacenado = repositorioPedido.guardar(pedido);

        assertNotNull(pedidoAlmacenado);
        assertEquals("PED123", pedidoAlmacenado.getCodigo());
        assertEquals(EstadoPedido.En_Disenio, pedidoAlmacenado.getEstado());
    }

    @Test
    @Rollback
    public void testQueAlCrear3PedidosYSolicitarUnaListaDeTodosLosPedidosMeDevuelvaUnaListaCon3Pedidos(){
        Pedido pedido1 = new Pedido();
        pedido1.setCodigo("PED001");
        pedido1.setEstado(EstadoPedido.En_Disenio);
        repositorioPedido.guardar(pedido1);

        Pedido pedido2 = new Pedido();
        pedido2.setCodigo("PED002");
        pedido2.setEstado(EstadoPedido.En_Fabricacion);
        repositorioPedido.guardar(pedido2);

        Pedido pedido3 = new Pedido();
        pedido3.setCodigo("PED003");
        pedido3.setEstado(EstadoPedido.Entregado);
        repositorioPedido.guardar(pedido3);

        List<Pedido> pedidos = repositorioPedido.obtenerTodos();

        assertNotNull(pedidos);
        assertEquals(3, pedidos.size());
    }

    @Test
    @Rollback
    public void testQueAlCrear2PedidosEnDisenioY1EntregadoYSoliciteUnaListaConLosPedidosEntregadosMeDevuelvaUnaListaCon1Elemento(){
        Pedido pedido1 = new Pedido();
        pedido1.setCodigo("PED001");
        pedido1.setEstado(EstadoPedido.En_Disenio);
        repositorioPedido.guardar(pedido1);

        Pedido pedido2 = new Pedido();
        pedido2.setCodigo("PED002");
        pedido2.setEstado(EstadoPedido.En_Disenio);
        repositorioPedido.guardar(pedido2);

        Pedido pedido3 = new Pedido();
        pedido3.setCodigo("PED003");
        pedido3.setEstado(EstadoPedido.Entregado);
        repositorioPedido.guardar(pedido3);

        List<Pedido> pedidosEntregados = repositorioPedido.obtenerEntregados();

        assertNotNull(pedidosEntregados);
        assertEquals(1, pedidosEntregados.size());
    }

    @Test
    @Rollback
    public void testQueMePermitaAsignarCorrectamente2MueblesAUnPedido(){
        Pedido pedido = new Pedido();
        pedido.setCodigo("PED123");
        pedido.setEstado(EstadoPedido.En_Disenio);

        Pedido pedidoAlmacenado = repositorioPedido.guardar(pedido);

        Mueble mueble1 = new Mueble();
        mueble1.setCodigo("MUEBLE123");
        mueble1.setCosto(150.0);
        mueble1.setTipo(TipoMueble.CAMA);

        Mueble mueble2 = new Mueble();
        mueble2.setCodigo("MUEBLE456");
        mueble2.setCosto(200.0);
        mueble2.setTipo(TipoMueble.CAMA);

        pedidoAlmacenado.agregarMueble(mueble1);
        pedidoAlmacenado.agregarMueble(mueble2);

        Pedido pedidoModificado = repositorioPedido.modificar(pedido);

        Double precio = pedidoModificado.calcularPrecioTotal();

        assertNotNull(pedidoModificado);
        assertEquals(2, pedidoModificado.getMuebles().size());
        assertEquals(350.0, precio);
    }

    @Test
    @Rollback
    public void testQueMePermitaCambiarElEstadoDeUnPedidoDeNoIniciadoAEnFabricacion(){
        Pedido pedido = new Pedido();
        pedido.setCodigo("PED123");
        pedido.setEstado(EstadoPedido.No_Iniciado);

        Pedido pedidoAlmacenado = repositorioPedido.guardar(pedido);

        pedidoAlmacenado.setEstado(EstadoPedido.En_Fabricacion);

        Pedido pedidoModificado = repositorioPedido.modificar(pedidoAlmacenado);

        assertNotNull(pedidoModificado);
        assertEquals(EstadoPedido.En_Fabricacion, pedidoModificado.getEstado());
    }

    @Test
    @Rollback
    public void testQueLuegoDeCrearUnPedidoYVerificarLaExistenciaDelCodigoMeDevuelvaTrue(){
        Pedido pedido = new Pedido();
        pedido.setCodigo("PED123");
        pedido.setEstado(EstadoPedido.No_Iniciado);

        repositorioPedido.guardar(pedido);

        Boolean existe = repositorioPedido.existeCodigo("PED123");

        assertEquals(true, existe);
    }

    @Test
    @Rollback
    public void testQueAlBuscarUnCodigoQueNoExisteEnLaBaseDeDatosMeDevuelvaFalse(){
        Pedido pedido = new Pedido();
        pedido.setCodigo("PED123");
        pedido.setEstado(EstadoPedido.No_Iniciado);

        repositorioPedido.guardar(pedido);

        Boolean existe = repositorioPedido.existeCodigo("PED1234");

        assertEquals(false, existe);
    }
}
