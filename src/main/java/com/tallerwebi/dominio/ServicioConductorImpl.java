package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ConductorViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("servicioConductor")
public class ServicioConductorImpl implements ServicioConductor {

    private RepositorioConductor repositorioConductor;

    @Autowired
    public ServicioConductorImpl(RepositorioConductor repositorioConductor) {
        this.repositorioConductor = repositorioConductor;
    }

    @Override
    public Conductor crear(ConductorViewModel conductorViewModel) {
        Conductor conductor = new Conductor();
        conductor.setNombre(conductorViewModel.getNombre());
        conductor.setEdad(conductorViewModel.getEdad());
        conductor.setSexo(conductorViewModel.getSexo());
        conductor.setCedula(conductorViewModel.getCedula());
        conductor.setEstado(EstadoConductor.Habilitado);

        return repositorioConductor.crear(conductor);
    }

    @Override
    public Conductor modificar(ConductorViewModel conductorViewModel) {
        Conductor conductor = repositorioConductor.buscar(conductorViewModel.getId());
        conductor.setNombre(conductorViewModel.getNombre());
        conductor.setEdad(conductorViewModel.getEdad());
        conductor.setSexo(conductorViewModel.getSexo());
        conductor.setCedula(conductorViewModel.getCedula());
        conductor.setEstado(conductorViewModel.getEstado());
        return repositorioConductor.modificar(conductor);
    }

    @Override
    public List<Conductor> buscarTodosHabilitados() {
        return repositorioConductor.buscarHabilitados();
    }

    @Override
    public List<Conductor> buscarTodosInhabilitados() {
        return repositorioConductor.buscarInhabilitados();
    }

    @Override
    public List<Conductor> buscarTodos() {
        return repositorioConductor.buscarTodos();
    }

    @Override
    public Conductor buscarPorId(Long conductor) {
        return repositorioConductor.buscar(conductor);
    }

    @Override
    public void cambiarEstado(Long id) {
        Conductor conductor = repositorioConductor.buscar(id);
        if (conductor.getEstado() == EstadoConductor.Habilitado) {
            conductor.setEstado(EstadoConductor.Inhabilitado);
        } else {
            conductor.setEstado(EstadoConductor.Habilitado);
        }
        repositorioConductor.modificar(conductor);
    }
}
