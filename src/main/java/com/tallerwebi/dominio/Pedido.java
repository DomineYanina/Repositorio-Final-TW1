package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private EstadoPedido estado;
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mueble> muebles = new ArrayList<>();

    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public List<Mueble> getMuebles() {
        return muebles;
    }

    public void setMuebles(List<Mueble> muebles) {
        this.muebles = muebles;
    }

    public void agregarMueble(Mueble mueble) {
        muebles.add(mueble);
        mueble.setPedido(this);
    }

    public void eliminarMueble(Mueble mueble) {
        muebles.remove(mueble);
        mueble.setPedido(null);
    }

    public Double calcularPrecioTotal() {
        return muebles.stream().mapToDouble(Mueble::getCosto).sum();
    }
}
