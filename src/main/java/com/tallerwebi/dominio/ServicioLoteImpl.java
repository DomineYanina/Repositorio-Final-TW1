package com.tallerwebi.dominio;

import com.tallerwebi.presentacion.LoteViewModel;
import com.tallerwebi.presentacion.SombreroViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("servicioLote")
public class ServicioLoteImpl implements ServicioLote {

    private RepositorioLote repositorioLote;

    @Autowired
    public ServicioLoteImpl(RepositorioLote repositorioLote) {
        this.repositorioLote = repositorioLote;
    }


    @Override
    public Lote crearLote(LoteViewModel loteViewModel) {
        Lote loteResultado = null;
        Lote loteEncontrado = repositorioLote.buscarPorId(loteViewModel.getId());
        if (loteEncontrado != null) {
            throw new IllegalArgumentException("El lote con ID " + loteViewModel.getId() + " ya existe.");
        } else {
            Lote lote = new Lote();
            lote.setId(loteViewModel.getId());
            lote.setEstado(loteViewModel.getEstado());
            lote.setSombreros(loteViewModel.getSombreros());
            loteResultado = repositorioLote.agregar(lote);
        }
        return loteResultado;
    }

    @Override
    public Lote agregarSombreros(LoteViewModel loteViewModel, SombreroViewModel sombrero, int i) {
        Sombrero sombrero1 = new Sombrero();
        sombrero1.setId(sombrero.getId());
        sombrero1.setTipo(sombrero.getTipo());
        sombrero1.setValorProduccion(sombrero.getValorProduccion());

        Lote lote = repositorioLote.buscarPorId(loteViewModel.getId());
        for(int j = 0; j < i - 1; j++) {
            lote.agregarSombrero(sombrero1);
        }
        return repositorioLote.modificar(lote);
    }

    @Override
    public Lote avanzarEstado(LoteViewModel loteViewModel) {
        Lote loteEncontrado = repositorioLote.buscarPorId(loteViewModel.getId());
        switch (loteEncontrado.getEstado()){
            case No_Iniciado:
                loteEncontrado.setEstado(EstadoLote.En_Progreso);
                break;
            case En_Progreso:
                loteEncontrado.setEstado(EstadoLote.Completado);
                break;
            case Completado:
                break;
        }
        return repositorioLote.modificar(loteEncontrado);
    }

    @Override
    public List<Lote> obtenerLotesFinalizados() {
        List<Lote> lotesFinalizados = repositorioLote.buscarLotesFinalizados();
        return lotesFinalizados;
    }

    @Override
    public List<Lote> obtenerTodosLosLotes() {
        return repositorioLote.obtenerTodosLosLotes();
    }
}
