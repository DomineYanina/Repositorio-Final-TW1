package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioProducto {
    Producto guardar(Producto producto);

    Producto modificar(Producto productoAlmacenado);

    List<Producto> buscarPorCategoria(Categoria categoria);

    List<Producto> buscarPorStock(Long stock);
}
