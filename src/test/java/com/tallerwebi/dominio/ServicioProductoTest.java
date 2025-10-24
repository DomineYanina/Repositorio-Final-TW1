package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ProductoViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ServicioProductoTest {
    @Mock
    private RepositorioProducto repositorioProducto;

    @InjectMocks
    private ServicioProductoImpl servicioProducto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQueSiTengo3ProductosConCategoriaElectronicaY2ConCategoriaHogarYSolicitoLosQueTienenCategoriaHogarMeDevuelvaUnaListaCon2Productos(){
        Producto producto1 = new Producto();
        producto1.setCategoria(Categoria.ELECTRONICA);
        producto1.setNombre("Electronica1");
        producto1.setStock(20L);

        Producto producto2 = new Producto();
        producto1.setCategoria(Categoria.ELECTRONICA);
        producto1.setNombre("Electronica2");
        producto1.setStock(20L);

        Producto producto3 = new Producto();
        producto1.setCategoria(Categoria.ELECTRONICA);
        producto1.setNombre("Electronica3");
        producto1.setStock(20L);

        Producto producto4 = new Producto();
        producto1.setCategoria(Categoria.HOGAR);
        producto1.setNombre("Hogar1");
        producto1.setStock(20L);

        Producto producto5 = new Producto();
        producto1.setCategoria(Categoria.HOGAR);
        producto1.setNombre("Hogar2");
        producto1.setStock(20L);

        List<Producto> productos = List.of(producto4,producto5);

        when(repositorioProducto.buscarPorCategoria(any(Categoria.class))).thenReturn(productos);

        List<ProductoViewModel> productosObtenidos = servicioProducto.buscarPorCategoria(Categoria.HOGAR);

        assertNotNull(productosObtenidos);
        assertEquals(2,productosObtenidos.size());
    }

    @Test
    public void testQueSiTengo3ProductosConStock50Y2ConStock25YSolicitoLosQueTienenStockMayorA10MeDevuelvaUnaListaCon5Productos(){
        Producto producto1 = new Producto();
        producto1.setCategoria(Categoria.ELECTRONICA);
        producto1.setNombre("Electronica1");
        producto1.setStock(50L);

        Producto producto2 = new Producto();
        producto1.setCategoria(Categoria.ELECTRONICA);
        producto1.setNombre("Electronica2");
        producto1.setStock(50L);

        Producto producto3 = new Producto();
        producto1.setCategoria(Categoria.ELECTRONICA);
        producto1.setNombre("Electronica3");
        producto1.setStock(50L);

        Producto producto4 = new Producto();
        producto1.setCategoria(Categoria.HOGAR);
        producto1.setNombre("Hogar1");
        producto1.setStock(25L);

        Producto producto5 = new Producto();
        producto1.setCategoria(Categoria.HOGAR);
        producto1.setNombre("Hogar2");
        producto1.setStock(25L);

        Long stock = 15L;

        List<Producto> productos = List.of(producto1,producto2,producto3,producto4,producto5);

        when(repositorioProducto.buscarPorStock(anyLong())).thenReturn(productos);

        List<ProductoViewModel> productosObtenidos = servicioProducto.buscarPorStock(stock);

        assertNotNull(productosObtenidos);
        assertEquals(5,productosObtenidos.size());
    }
}
