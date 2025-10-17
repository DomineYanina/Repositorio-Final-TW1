package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioConductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
public class ControladorConductor {

    private ServicioConductor servicioConductor;

    @Autowired
    public ControladorConductor(ServicioConductor servicioConductor) {
        this.servicioConductor = servicioConductor;
    }

    @GetMapping("/irARegistrarConductor")
    public ModelAndView irARegistrar() {
        ModelAndView mav = new ModelAndView("registrarConductor");
        mav.addObject("conductorDTO", new ConductorViewModel());
        return mav;
    }

    @PostMapping("/registrarConductor")
    @Transactional
    public ModelAndView registrar(@ModelAttribute("conductorDTO") ConductorViewModel conductorViewModel) {
        servicioConductor.crear(conductorViewModel);
        ModelAndView mav = new ModelAndView("listarConductores");
        mav.addObject("conductoresHabilitados", servicioConductor.buscarTodosHabilitados());
        mav.addObject("todosLosConductores", servicioConductor.buscarTodos());
        return mav;
    }

    @PostMapping("/cambiarEstadoconductor")
    @Transactional
    public ModelAndView cambiarEstadoConductor(@RequestParam ("id") Long id) {
        servicioConductor.cambiarEstado(id);
        ModelAndView mav = new ModelAndView("listarConductores");
        mav.addObject("conductoresHabilitados", servicioConductor.buscarTodosHabilitados());
        mav.addObject("todosLosConductores", servicioConductor.buscarTodos());
        return mav;
    }
}
