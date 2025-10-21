package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Mueble {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private Double costo;
    private TipoMueble tipo;
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public Mueble() {
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

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public TipoMueble getTipo() {
        return tipo;
    }

    public void setTipo(TipoMueble tipo) {
        this.tipo = tipo;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
