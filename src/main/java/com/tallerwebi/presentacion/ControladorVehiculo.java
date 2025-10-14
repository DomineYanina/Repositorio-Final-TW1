package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioVehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorVehiculo {

    private ServicioVehiculo servicioVehiculo;

    @Autowired
    public ControladorVehiculo(ServicioVehiculo servicioVehiculo) {
        this.servicioVehiculo = servicioVehiculo;
    }
}
