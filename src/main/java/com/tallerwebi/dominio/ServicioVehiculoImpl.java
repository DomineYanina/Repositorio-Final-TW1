package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioVehiculo")
public class ServicioVehiculoImpl implements ServicioVehiculo {

    private RepositorioVehiculo repositorioVehiculo;

    @Autowired
    public ServicioVehiculoImpl(RepositorioVehiculo repositorioVehiculo) {
        this.repositorioVehiculo = repositorioVehiculo;
    }
}
