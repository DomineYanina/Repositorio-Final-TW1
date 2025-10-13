package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Lote;
import com.tallerwebi.dominio.ServicioLote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    public ModelAndView crearLote(LoteViewModel loteDTO) {
        ModelAndView mav;
        Lote lote = servicioLote.crearLote(loteDTO);
        if(lote!=null){
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
            mav.addObject("listaLotes", listaLotesDTO);
        } else {
            mav = new ModelAndView("crearLote");
            mav.addObject("error", "Código existente en otro lote.");
        }
        return mav;
    }
}
