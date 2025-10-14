package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoConductor;

public class ConductorViewModel {

    private Long id;
    private String nombre;
    private String cedula;
    private int edad;
    private String nacionalidad;
    private String sexo;
    private EstadoConductor estado;
    private Long vehiculo;

    public ConductorViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public EstadoConductor getEstado() {
        return estado;
    }

    public void setEstado(EstadoConductor estado) {
        this.estado = estado;
    }

    public Long getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Long vehiculo) {
        this.vehiculo = vehiculo;
    }
}
