package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoVehiculo;

public class VehiculoViewModel {
    private Long id;
    private String patente;
    private String marca;
    private EstadoVehiculo estado;
    private Long conductor;

    public VehiculoViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public EstadoVehiculo getEstado() {
        return estado;
    }

    public void setEstado(EstadoVehiculo estado) {
        this.estado = estado;
    }

    public Long getConductor() {
        return conductor;
    }

    public void setConductor(Long conductor) {
        this.conductor = conductor;
    }
}
