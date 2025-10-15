package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.VehiculoViewModel;

import java.util.List;

public interface ServicioVehiculo {
    Vehiculo crear(VehiculoViewModel vehiculoViewModel);

    Vehiculo modificar(VehiculoViewModel vehiculoAModificar);

    Vehiculo asignarConductor(VehiculoViewModel vehiculoViewModel);

    List<Vehiculo> buscarTodosHabilitados();

    List<Vehiculo> buscarTodosInhabilitados();

    List<Vehiculo> buscarTodos();
}
