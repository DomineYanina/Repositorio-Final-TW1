package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioLote")
public class ServicioLoteImpl implements ServicioLote {

    private RepositorioLote repositorioLote;

    @Autowired
    public ServicioLoteImpl(RepositorioLote repositorioLote) {
        this.repositorioLote = repositorioLote;
    }


}
