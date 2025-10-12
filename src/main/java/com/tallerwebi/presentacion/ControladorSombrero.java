package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioSombrero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorSombrero {

    private ServicioSombrero servicioSombrero;

    @Autowired
    public ControladorSombrero(ServicioSombrero servicioSombrero) {
        this.servicioSombrero = servicioSombrero;
    }

}
