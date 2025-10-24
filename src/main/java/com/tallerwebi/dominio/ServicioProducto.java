package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ProductoViewModel;

import java.util.List;

public interface ServicioProducto {
    List<ProductoViewModel> buscarPorCategoria(Categoria categoria);

    List<ProductoViewModel> buscarPorStock(Long stock);
}
