package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoLote;
import com.tallerwebi.dominio.Lote;
import com.tallerwebi.dominio.ServicioLote;
import com.tallerwebi.dominio.excepcion.CodigoExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorLote {

    private ServicioLote servicioLote;

    @Autowired
    public ControladorLote(ServicioLote servicioLote) {
        this.servicioLote = servicioLote;
    }

    @GetMapping("irACrearLote")
    public ModelAndView irACrearLote() {
        ModelAndView mv = new ModelAndView("crearLote");
        mv.addObject("loteDTO", new LoteViewModel());
        mv.addObject("estados", EstadoLote.values());
        return mv;
    }

    @PostMapping("/crearLote")
    public ModelAndView crearLote(@ModelAttribute ("loteDTO") LoteViewModel loteDTO) {
        ModelAndView mav;
        try{
            Lote lote = servicioLote.crearLote(loteDTO);
            mav = new ModelAndView("lotes");

            List<Lote> listaLotes = servicioLote.obtenerTodosLosLotes();
            List<LoteViewModel> listaLotesDTO = new ArrayList<>();
            for (Lote lote1 : listaLotes) {
                LoteViewModel loteViewModel = new LoteViewModel();
                loteViewModel.setId(lote1.getId());
                loteViewModel.setEstado(lote1.getEstado());
                loteViewModel.setSombreros(lote1.getSombreros());
                listaLotesDTO.add(loteViewModel);
            }

            List<Lote> listaLotesFinalizados = servicioLote.obtenerLotesFinalizados();
            List<LoteViewModel> listaLotesFinalizadosDTO = new ArrayList<>();
            for (Lote lote2 : listaLotesFinalizados) {
                LoteViewModel loteViewModel = new LoteViewModel();
                loteViewModel.setId(lote2.getId());
                loteViewModel.setEstado(lote2.getEstado());
                loteViewModel.setSombreros(lote2.getSombreros());
                listaLotesFinalizadosDTO.add(loteViewModel);
            }

            mav.addObject("listaLotes", listaLotesDTO);
            mav.addObject("listaLotesFinalizados", listaLotesFinalizadosDTO);
        }
        catch (CodigoExistenteException e){
            mav = new ModelAndView("crearLote");
            mav.addObject("error", e.getMessage());
        }
        return mav;
    }

    @GetMapping("/irAAgregarSombreros")
    public ModelAndView irAAgregarSombreros(@RequestParam("idLote") Long idLote) {
        ModelAndView mav = new ModelAndView("agregarSombreros");
        Lote lote = servicioLote.obtenerLotePorId(idLote);
        LoteViewModel loteDTO = new LoteViewModel();
        loteDTO.setId(lote.getId());
        loteDTO.setEstado(lote.getEstado());
        loteDTO.setSombreros(lote.getSombreros());
        mav.addObject("lote", loteDTO);
        return mav;
    }

    @PostMapping("/agregarSombrero")
    public ModelAndView agregarSombrero(@ModelAttribute ("sombrero") SombreroViewModel sombreroViewModel,
                                        @RequestParam("idLote") long idLote,
                                        @RequestParam("cantidad") int cantidad) {
        Lote lote = servicioLote.obtenerLotePorId(idLote);
        LoteViewModel loteDTO = new LoteViewModel();
        loteDTO.setId(lote.getId());
        loteDTO.setEstado(lote.getEstado());
        loteDTO.setSombreros(lote.getSombreros());

        servicioLote.agregarSombreros(loteDTO, sombreroViewModel, cantidad);

        ModelAndView mav = new ModelAndView("lotes");
        List<Lote> listaLotes = servicioLote.obtenerTodosLosLotes();
        List<LoteViewModel> listaLotesDTO = new ArrayList<>();
        for (Lote lote1 : listaLotes) {
            LoteViewModel loteViewModel = new LoteViewModel();
            loteViewModel.setId(lote1.getId());
            loteViewModel.setEstado(lote1.getEstado());
            loteViewModel.setSombreros(lote1.getSombreros());
            listaLotesDTO.add(loteViewModel);
        }

        List<Lote> listaLotesFinalizados = servicioLote.obtenerLotesFinalizados();
        List<LoteViewModel> listaLotesFinalizadosDTO = new ArrayList<>();
        for (Lote lote2 : listaLotesFinalizados) {
            LoteViewModel loteViewModel = new LoteViewModel();
            loteViewModel.setId(lote2.getId());
            loteViewModel.setEstado(lote2.getEstado());
            loteViewModel.setSombreros(lote2.getSombreros());
            listaLotesFinalizadosDTO.add(loteViewModel);
        }

        mav.addObject("listaLotes", listaLotesDTO);
        mav.addObject("listaLotesFinalizados", listaLotesFinalizadosDTO);

        return mav;
    }
}
