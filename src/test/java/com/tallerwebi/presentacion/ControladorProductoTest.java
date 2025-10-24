package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.ServicioProducto;
import com.tallerwebi.dominio.ServicioProductoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class ControladorProductoTest {
    @Mock
    private ServicioProductoImpl servicioProducto;

    @InjectMocks
    private ControladorProducto controladorProducto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void queAlSolicitarIrALaVistaParaFiltrarPorStockMeDevuelvaLaVistaCorrecta(){
        ModelAndView mav = controladorProducto.irAFiltrarPorStock();

        assertNotNull(mav);
        assertEquals(mav.getViewName(), "filtrarPorStock");
    }

    @Test
    public void queAlSolicitarIrALaVistaParaFiltrarPorCategoriaMeDevuelvaLaVistaCorrecta(){
        ModelAndView mav = controladorProducto.irAFiltrarPorCategoria();

        assertNotNull(mav);
        assertEquals(mav.getViewName(), "filtrarPorCategoria");
    }

    @Test
    public void testQueAlSolicitarFiltrarPorStockMeDevuelvaUnaListaCon3ProductosYMeRedirijaALaVistaCorrectaEnLaCualSeRendericeLaVista(){
        ProductoViewModel p1 = new ProductoViewModel();
        p1.setNombre("Hogar1");
        p1.setStock(25L);
        p1.setCategoria(Categoria.ELECTRONICA);
        p1.setId(1L);

        ProductoViewModel p2 = new ProductoViewModel();
        p1.setNombre("Hogar1");
        p1.setStock(35L);
        p1.setCategoria(Categoria.ELECTRONICA);
        p1.setId(2L);

        ProductoViewModel p3 = new ProductoViewModel();
        p1.setNombre("Hogar1");
        p1.setStock(15L);
        p1.setCategoria(Categoria.ELECTRONICA);
        p1.setId(3L);

        List<ProductoViewModel> listaProductos = List.of(p1, p2, p3);

        Long stock = 10L;

        when(servicioProducto.buscarPorStock(anyLong())).thenReturn(listaProductos);

        ModelAndView mav = controladorProducto.filtrarPorStock(stock);

        assertNotNull(mav);
        assertEquals("listaPorStock",mav.getViewName());
        assertThat(mav.getModel(),hasKey("listaProductos"));
        assertEquals(mav.getModel().get("listaProductos"), listaProductos);
    }


    @Test
    public void testQueAlSolicitarFiltrarPorCategoriaMeDevuelvaUnaListaCon3ProductosYMeRedirijaALaVistaCorrectaEnLaCualSeRendericeLaVista(){
        ProductoViewModel p1 = new ProductoViewModel();
        p1.setNombre("Hogar1");
        p1.setStock(25L);
        p1.setCategoria(Categoria.ELECTRONICA);
        p1.setId(1L);

        ProductoViewModel p2 = new ProductoViewModel();
        p1.setNombre("Hogar1");
        p1.setStock(35L);
        p1.setCategoria(Categoria.ELECTRONICA);
        p1.setId(2L);

        ProductoViewModel p3 = new ProductoViewModel();
        p1.setNombre("Hogar1");
        p1.setStock(15L);
        p1.setCategoria(Categoria.ELECTRONICA);
        p1.setId(3L);

        List<ProductoViewModel> listaProductos = List.of(p1, p2, p3);

        when(servicioProducto.buscarPorCategoria(any(Categoria.class))).thenReturn(listaProductos);

        ModelAndView mav = controladorProducto.filtrarPorCategoria(Categoria.ELECTRONICA);

        assertNotNull(mav);
        assertEquals("listaPorCategoria",mav.getViewName());
        assertThat(mav.getModel(),hasKey("listaProductos"));
        assertEquals(mav.getModel().get("listaProductos"), listaProductos);
    }

}
