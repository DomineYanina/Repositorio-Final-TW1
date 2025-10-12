package com.tallerwebi.presentacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorLote {

    private ControladorLote controladorLote;

    @Autowired
    public ControladorLote(ControladorLote controladorLote) {
        this.controladorLote = controladorLote;
    }
}
