package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.AsignacionConductorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorVehiculo {

    private ServicioVehiculo servicioVehiculo;
    private ServicioConductor servicioConductor;

    @Autowired
    public ControladorVehiculo(ServicioVehiculo servicioVehiculo,
                               ServicioConductor servicioConductor) {
        this.servicioVehiculo = servicioVehiculo;
        this.servicioConductor = servicioConductor;
    }

    @GetMapping("/irARegistrarVehiculo")
    public ModelAndView irARegistrar() {
        ModelAndView mav = new ModelAndView("registrarVehiculo");
        mav.addObject("vehiculoDTO", new VehiculoViewModel());
        return mav;
    }

    @PostMapping("/registrarVehiculo")
    public ModelAndView registrar(VehiculoViewModel vehiculoViewModel) {
        servicioVehiculo.crear(vehiculoViewModel);
        ModelAndView mav = new ModelAndView("listarVehiculos");
        mav.addObject("vehiculosHabilitados", servicioVehiculo.buscarTodosHabilitados());
        mav.addObject("todosLosVehiculos", servicioVehiculo.buscarTodos());
        return mav;
    }

    @PostMapping("/asignarConductorAVehiculo")
    public ModelAndView asignarConductor(VehiculoViewModel vehiculoViewModel) {
        ModelAndView mav = new ModelAndView("listarVehiculos");
        mav.addObject("vehiculosHabilitados", servicioVehiculo.buscarTodosHabilitados());
        mav.addObject("todosLosVehiculos", servicioVehiculo.buscarTodos());

        try{
            servicioVehiculo.asignarConductor(vehiculoViewModel);
            mav.addObject("exito", "El conductor se ha asignado correctamente");
        } catch (AsignacionConductorException e){
            mav.addObject("error", e.getMessage());
        }
        return mav;
    }
}
