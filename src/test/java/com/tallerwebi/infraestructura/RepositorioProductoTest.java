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
public class RepositorioProductoTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioProducto repositorioProducto;

    @BeforeEach
    public void setUp() {
        repositorioProducto = new RepositorioProductoImpl(sessionFactory);
    }

    @Test
    @Rollback
    public void testQueMePermitaCrearUnProductoYQueSeAlmaceneCorrectamenteEnLaBaseDeDatos() {
        Producto producto = new Producto();
        producto.setNombre("Laptop");
        producto.setCategoria(Categoria.ELECTRONICA);
        producto.setStock(10L);

        Producto productoAlmacenado = repositorioProducto.guardar(producto);

        assertNotNull(productoAlmacenado.getId());

        assertEquals("Laptop", productoAlmacenado.getNombre());
        assertEquals(Categoria.ELECTRONICA, productoAlmacenado.getCategoria());
        assertEquals(10L, productoAlmacenado.getStock());
    }

    @Test
    @Rollback
    public void testQueMePermitaModificarElStockDeUnProductoYPersistaElCambioEnLaBaseDeDatos(){
        Producto producto = new Producto();
        producto.setNombre("Laptop");
        producto.setCategoria(Categoria.ELECTRONICA);
        producto.setStock(10L);

        Producto productoAlmacenado = repositorioProducto.guardar(producto);

        productoAlmacenado.setStock(20L);

        Producto productoModificado = repositorioProducto.modificar(productoAlmacenado);

        assertNotNull(productoModificado.getId());
        assertEquals("Laptop", productoModificado.getNombre());
        assertEquals(Categoria.ELECTRONICA, productoModificado.getCategoria());
        assertEquals(20L, productoModificado.getStock());
    }

    @Test
    @Rollback
    public void testQueAlCrear2ProductosCuyaCategoriaEsElectronicaY3CuyaCategoriaEsHogarYSoliciteLosProductosCuyaCategoriaEsElectronicaMeDevuelvaUnaListaCon2Productos(){
        Producto producto = new Producto();
        producto.setStock(10L);
        producto.setCategoria(Categoria.ELECTRONICA);
        producto.setNombre("Laptop");

        Producto producto2 = new Producto();
        producto2.setStock(20L);
        producto2.setCategoria(Categoria.ELECTRONICA);
        producto2.setNombre("Tablet");

        Producto producto3 = new Producto();
        producto3.setStock(30L);
        producto3.setCategoria(Categoria.HOGAR);
        producto3.setNombre("Aspiradora");

        Producto producto4 = new Producto();
        producto4.setStock(40L);
        producto4.setCategoria(Categoria.HOGAR);
        producto4.setNombre("Mesa");

        Producto producto5 = new Producto();
        producto5.setStock(50L);
        producto5.setCategoria(Categoria.HOGAR);
        producto5.setNombre("Lavarropas");

        Producto productoAlmacenado1 = repositorioProducto.guardar(producto);
        Producto productoAlmacenado2 = repositorioProducto.guardar(producto2);
        Producto productoAlmacenado3 = repositorioProducto.guardar(producto3);
        Producto productoAlmacenado4 = repositorioProducto.guardar(producto4);
        Producto productoAlmacenado5 = repositorioProducto.guardar(producto5);

        List<Producto> productosElectronicos = repositorioProducto.buscarPorCategoria(Categoria.ELECTRONICA);

        assertEquals(2, productosElectronicos.size());
        assertNotNull(productosElectronicos);
        assertEquals(productoAlmacenado1.getNombre(), productosElectronicos.get(0).getNombre());
        assertEquals(productoAlmacenado2.getNombre(), productosElectronicos.get(1).getNombre());
    }

    @Test
    @Rollback
    public void testQueAlCrear2ProductosCuyoStockEs50YY1CuyoStockEs20YSoliciteLosProductosCuyoStockSeaMayorA30MeDevuelvaLosMeDevuelvaUnaListaCon2Productos(){
        Producto producto = new Producto();
        producto.setStock(50L);
        producto.setCategoria(Categoria.ELECTRONICA);
        producto.setNombre("Laptop");

        Producto producto2 = new Producto();
        producto2.setStock(50L);
        producto2.setCategoria(Categoria.ELECTRONICA);
        producto2.setNombre("Tablet");

        Producto producto3 = new Producto();
        producto3.setStock(20L);
        producto3.setCategoria(Categoria.HOGAR);
        producto3.setNombre("Aspiradora");

        Producto productoAlmacenado1 = repositorioProducto.guardar(producto);
        Producto productoAlmacenado2 = repositorioProducto.guardar(producto2);
        Producto productoAlmacenado3 = repositorioProducto.guardar(producto3);

        Long stock = 30L;

        List<Producto> productos = repositorioProducto.buscarPorStock(stock);

        assertEquals(2, productos.size());
        assertNotNull(productos);
        assertEquals(productoAlmacenado1.getNombre(), productos.get(0).getNombre());
        assertEquals(productoAlmacenado2.getNombre(), productos.get(1).getNombre());
    }

    @Test
    @Rollback
    public void testQueAlCrear2ProductosCuyoStockEs50YY1CuyoStockEs20YSoliciteLosProductosCuyoStockSeaMayorA60MeDevuelvaLosMeDevuelvaUnaListaVacia(){
        Producto producto = new Producto();
        producto.setStock(50L);
        producto.setCategoria(Categoria.ELECTRONICA);
        producto.setNombre("Laptop");

        Producto producto2 = new Producto();
        producto2.setStock(50L);
        producto2.setCategoria(Categoria.ELECTRONICA);
        producto2.setNombre("Tablet");

        Producto producto3 = new Producto();
        producto3.setStock(20L);
        producto3.setCategoria(Categoria.HOGAR);
        producto3.setNombre("Aspiradora");

        Producto productoAlmacenado1 = repositorioProducto.guardar(producto);
        Producto productoAlmacenado2 = repositorioProducto.guardar(producto2);
        Producto productoAlmacenado3 = repositorioProducto.guardar(producto3);

        Long stock = 60L;

        List<Producto> productos = repositorioProducto.buscarPorStock(stock);

        assertEquals(0, productos.size());
        assertNotNull(productos);
    }

}
