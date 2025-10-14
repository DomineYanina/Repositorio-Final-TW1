package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioConductor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorConductor {

    private ServicioConductor servicioConductor;

    @Autowired
    public ControladorConductor(ServicioConductor servicioConductor) {
        this.servicioConductor = servicioConductor;
    }
}
