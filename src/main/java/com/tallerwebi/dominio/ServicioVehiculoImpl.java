package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.VehiculoViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("servicioVehiculo")
public class ServicioVehiculoImpl implements ServicioVehiculo {

    private RepositorioVehiculo repositorioVehiculo;
    private RepositorioConductor repositorioConductor;

    @Autowired
    public ServicioVehiculoImpl(RepositorioVehiculo repositorioVehiculo,
                                RepositorioConductor repositorioConductor) {
        this.repositorioVehiculo = repositorioVehiculo;
        this.repositorioConductor = repositorioConductor;
    }

    @Override
    public Vehiculo crear(VehiculoViewModel vehiculoViewModel) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setMarca(vehiculoViewModel.getMarca());
        vehiculo.setEstado(EstadoVehiculo.Habilitado);
        return repositorioVehiculo.crear(vehiculo);
    }

    @Override
    public Vehiculo modificar(VehiculoViewModel vehiculoAModificar) {
        Vehiculo vehiculo = repositorioVehiculo.buscar(vehiculoAModificar.getId());
        vehiculo.setEstado(vehiculoAModificar.getEstado());
        vehiculo.setMarca(vehiculoAModificar.getMarca());
        return repositorioVehiculo.modificar(vehiculo);
    }

    @Override
    public Vehiculo asignarConductor(VehiculoViewModel vehiculoViewModel) {
        Conductor conductor = repositorioConductor.buscar(vehiculoViewModel.getConductor());
        Vehiculo vehiculo = repositorioVehiculo.buscar(vehiculoViewModel.getId());
        Vehiculo vehiculoResultado;
        if(((conductor.getEstado().equals(EstadoConductor.Habilitado))&&(vehiculo.getEstado().equals(EstadoVehiculo.Habilitado)))||(vehiculo.getConductor()!=null)){
            vehiculo.setConductor(conductor);
            vehiculoResultado = repositorioVehiculo.modificar(vehiculo);
        } else{
            vehiculoResultado = null;
        }
        return vehiculoResultado;
    }

    @Override
    public List<Vehiculo> buscarTodosHabilitados() {
        return repositorioVehiculo.buscarHabilitados();
    }

    @Override
    public List<Vehiculo> buscarTodosInhabilitados() {
        return repositorioVehiculo.buscarInhabilitados();
    }

    @Override
    public List<Vehiculo> buscarTodos() {
        return repositorioVehiculo.buscarTodos();
    }
}
