package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CodigoEventoExistente;
import com.tallerwebi.presentacion.EventoViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioEventoImpl implements ServicioEvento {

    private RepositorioEvento repositorioEvento;

    @Autowired
    public ServicioEventoImpl(RepositorioEvento repositorioEvento) {
        this.repositorioEvento = repositorioEvento;
    }

    @Override
    public EventoViewModel crearEvento(EventoViewModel eventoViewModel) {
        if(repositorioEvento.existeCodigo(eventoViewModel.getCodigo())) {
            throw new CodigoEventoExistente();
        }
        else{
            Evento evento = new Evento();
            evento.setCodigo(eventoViewModel.getCodigo());
            evento.setEstado(EstadoEvento.Planificado);

            Evento eventoCreado = repositorioEvento.guardar(evento);

            EventoViewModel eventoCreadoViewModel = new EventoViewModel();
            eventoCreadoViewModel.setId(eventoCreado.getId());
            eventoCreadoViewModel.setCodigo(eventoCreado.getCodigo());
            eventoCreadoViewModel.setEstado(eventoCreado.getEstado());

            return eventoCreadoViewModel;
        }
    }

    @Override
    public EventoViewModel avanzarEstado(EventoViewModel eventoViewModel) {
        Evento evento = repositorioEvento.buscarPorCodigo(eventoViewModel.getCodigo());

        switch(evento.getEstado()){
            case Planificado:
                evento.setEstado(EstadoEvento.EnCurso);
                break;
            case EnCurso:
                evento.setEstado(EstadoEvento.Finalizado);
                break;
            case Finalizado:
                // No hay avance posible
                break;
        }
        Evento eventoActualizado = repositorioEvento.modificar(evento);
        EventoViewModel eventoActualizadoViewModel = new EventoViewModel();
        eventoActualizadoViewModel.setId(eventoActualizado.getId());
        eventoActualizadoViewModel.setCodigo(eventoActualizado.getCodigo());
        eventoActualizadoViewModel.setEstado(eventoActualizado.getEstado());
        return  eventoActualizadoViewModel;
    }
}
