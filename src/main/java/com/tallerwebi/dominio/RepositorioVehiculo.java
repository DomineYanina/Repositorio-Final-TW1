package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioVehiculo {
    Vehiculo crear(Vehiculo vehiculo);

    Vehiculo modificar(Vehiculo vehiculoAlmacenado);

    Vehiculo buscar(long l);

    List<Vehiculo> buscarHabilitados();

    List<Vehiculo> buscarInhabilitados();

    List<Vehiculo> buscarTodos();
}
