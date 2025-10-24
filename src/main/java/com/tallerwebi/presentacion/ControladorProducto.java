package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.ServicioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.List;

@Controller
public class ControladorProducto {

    private ServicioProducto servicioProducto;

    @Autowired
    public ControladorProducto(ServicioProducto servicioProducto) {
        this.servicioProducto = servicioProducto;
    }

    @GetMapping("/irAFiltrarPorStock")
    public ModelAndView irAFiltrarPorStock() {
        ModelAndView mav = new ModelAndView("filtrarPorStock");
        return mav;
    }

    @GetMapping("/irAFiltrarPorCategoria")
    public ModelAndView irAFiltrarPorCategoria() {
        ModelAndView mav = new ModelAndView("filtrarPorCategoria");
        mav.addObject("Categorias",Categoria.values());
        return mav;
    }

    @GetMapping("/filtrarPorStock")
    @Transactional
    public ModelAndView filtrarPorStock(@RequestParam("stock") Long stock) {
        List<ProductoViewModel> productosObtenidos = servicioProducto.buscarPorStock(stock);
        ModelAndView mav = new ModelAndView("listaPorStock");
        mav.addObject("listaProductos", productosObtenidos);
        return mav;
    }

    @GetMapping("/filtrarPorCategoria")
    @Transactional
    public ModelAndView filtrarPorCategoria(Categoria categoria) {
        List<ProductoViewModel> productosObtenidos = servicioProducto.buscarPorCategoria(categoria);
        ModelAndView mav = new ModelAndView("listaPorCategoria");
        mav.addObject("listaProductos", productosObtenidos);
        return mav;
    }
}
