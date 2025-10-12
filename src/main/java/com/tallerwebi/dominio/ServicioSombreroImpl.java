package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioSombrero")
public class ServicioSombreroImpl implements ServicioSombrero {

    private RepositorioSombrero repositorioSombrero;

    @Autowired
    public ServicioSombreroImpl(RepositorioSombrero repositorioSombrero) {
        this.repositorioSombrero = repositorioSombrero;
    }
}
