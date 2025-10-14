package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioConductor")
public class ServicioConductorImpl implements ServicioConductor {

    private RepositorioVehiculo repositorioVehiculo;

    @Autowired
    public ServicioConductorImpl(RepositorioVehiculo repositorioVehiculo) {
        this.repositorioVehiculo = repositorioVehiculo;
    }

}
