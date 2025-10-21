package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Pedido;
import com.tallerwebi.dominio.ServicioPedido;
import com.tallerwebi.dominio.TipoMueble;
import com.tallerwebi.dominio.excepcion.CodigoPedidoExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPedido {
    private ServicioPedido servicioPedido;

    @Autowired
    public ControladorPedido(ServicioPedido servicioPedido) {
        this.servicioPedido = servicioPedido;
    }

    @GetMapping("/irACrearPedido")
    public ModelAndView irACrearUnNuevoPedido() {
        ModelAndView modeloVista = new ModelAndView("crearPedido");
        modeloVista.addObject("pedido", new PedidoViewModel());
        return modeloVista;
    }

    @PostMapping("/crearPedido")
    public ModelAndView crearPedido(@ModelAttribute ("pedido") PedidoViewModel pedidoViewModelEntrada) {
        ModelAndView modeloVista;
        try{
            servicioPedido.crearPedido(pedidoViewModelEntrada);
            modeloVista = new ModelAndView("listadoPedidos");
            modeloVista.addObject("pedidos", servicioPedido.obtenerTodos());
            modeloVista.addObject("pedidosEntregados", servicioPedido.obtenerEntregados());
            return modeloVista;
        } catch (CodigoPedidoExistente e){
            modeloVista = new ModelAndView("crearPedido");
            modeloVista.addObject("pedido", new PedidoViewModel());
            modeloVista.addObject("error", e.getMessage());
            return modeloVista;
        }
    }

    @GetMapping("/irAAgregarMuebleAPedido")
    public ModelAndView irAAgregarUnNuevoMuebleAPedido(@RequestParam("id") long id) {
        MuebleViewModel muebleViewModel = new MuebleViewModel();
        muebleViewModel.setPedido(id);

        ModelAndView modeloVista = new ModelAndView("agregarMuebleAPedido");
        modeloVista.addObject("muebleViewModel", muebleViewModel);
        modeloVista.addObject("tiposDeMuebles", TipoMueble.values());
        return modeloVista;
    }

    @PostMapping("/agregarMuebleAPedido")
    public ModelAndView agregarMuebleAPedido(@ModelAttribute("muebleViewModel") MuebleViewModel muebleViewModel,
                                             @RequestParam("cantidad") int cantidadMuebles) {
        PedidoViewModel pedidoViewModel = servicioPedido.buscarPedidoPorId(muebleViewModel.getPedido());
        Pedido pedidoActualizado = servicioPedido.agregarMuebleAPedido(pedidoViewModel, muebleViewModel, cantidadMuebles);

        ModelAndView modeloVista = new ModelAndView("listadoPedidos");
        modeloVista.addObject("pedidos", servicioPedido.obtenerTodos());
        modeloVista.addObject("pedidosEntregados", servicioPedido.obtenerEntregados());

        return modeloVista;
    }

    @PostMapping("/avanzarEstado")
    public ModelAndView avanzarEstado(@RequestParam("codigo") String codigo) {
        servicioPedido.avanzarEstadoPedido(codigo);

        ModelAndView modeloVista = new ModelAndView("listadoPedidos");
        modeloVista.addObject("pedidos", servicioPedido.obtenerTodos());
        modeloVista.addObject("pedidosEntregados", servicioPedido.obtenerEntregados());

        return modeloVista;
    }
}
