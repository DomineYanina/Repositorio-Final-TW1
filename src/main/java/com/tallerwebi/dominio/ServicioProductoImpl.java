package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.ProductoViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioProductoImpl implements ServicioProducto {

    private RepositorioProducto repositorioProducto;

    @Autowired
    public ServicioProductoImpl(RepositorioProducto repositorioProducto) {
        this.repositorioProducto = repositorioProducto;
    }

    @Override
    public List<ProductoViewModel> buscarPorCategoria(Categoria categoria) {
        List<Producto> productosObtenidos = repositorioProducto.buscarPorCategoria(categoria);

        List<ProductoViewModel> productosVM = new ArrayList<>();

        for (Producto producto : productosObtenidos) {
            ProductoViewModel prodVM = new ProductoViewModel();
            prodVM.setNombre(producto.getNombre());
            prodVM.setCategoria(producto.getCategoria());
            prodVM.setStock(producto.getStock());
            prodVM.setId(producto.getId());
            productosVM.add(prodVM);
        }

        return productosVM;
    }

    @Override
    public List<ProductoViewModel> buscarPorStock(Long stock) {
        List<Producto> productosObtenidos = repositorioProducto.buscarPorStock(stock);

        List<ProductoViewModel> productosVM = new ArrayList<>();

        for (Producto producto : productosObtenidos) {
            ProductoViewModel prodVM = new ProductoViewModel();
            prodVM.setNombre(producto.getNombre());
            prodVM.setCategoria(producto.getCategoria());
            prodVM.setStock(producto.getStock());
            prodVM.setId(producto.getId());
            productosVM.add(prodVM);
        }

        return productosVM;
    }
}
