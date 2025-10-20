package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPublicacion;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.CodigoEdicionExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPresentacion {

    private ServicioPublicacion servicioPublicacion;

    @Autowired
    public ControladorPresentacion(ServicioPublicacion servicioPublicacion) {
        this.servicioPublicacion = servicioPublicacion;
    }

    public ModelAndView irACrearPresentacion() {
        ModelAndView modeloVista = new ModelAndView("crearPublicacion");
        modeloVista.addObject("publicacionVM", new PublicacionViewModel());
        return modeloVista;
    }

    public ModelAndView crearPresentacion(PublicacionViewModel publicacionVM) {
        ModelAndView modeloVista;
        try{
            servicioPublicacion.crearPublicacion(publicacionVM);
            modeloVista = new ModelAndView("listaPublicaciones");
            modeloVista.addObject("listaPublicaciones", servicioPublicacion.obtenerTodasLasPublicaciones());
            modeloVista.addObject("publicacionesArchivadas", servicioPublicacion.obtenerPublicacionesArchivadas());
        } catch (CodigoEdicionExistenteException e) {
            modeloVista = new ModelAndView("crearPublicacion");
            modeloVista.addObject("publicacionVM", publicacionVM);
            modeloVista.addObject("error", e.getMessage());
        }
        return modeloVista;
    }

    public ModelAndView irAAgregarEdicion(long l) {
        ModelAndView modeloVista = new ModelAndView("agregarEdicion");
        PublicacionViewModel publicacionVM = servicioPublicacion.buscarPorCodigo(l);
        modeloVista.addObject("publicacionVM", publicacionVM);
        modeloVista.addObject("edicionVM", new EdicionViewModel());
        return modeloVista;
    }

    public ModelAndView agregarEdicion(long l, EdicionViewModel edicionVM) {

        ModelAndView modeloVista = new ModelAndView("listaPublicaciones");
        
        PublicacionViewModel publicacionVM = servicioPublicacion.buscarPorCodigo(l);
        // Lógica para agregar la edición a la publicación
        modeloVista.addObject("listaPublicaciones", servicioPublicacion.obtenerTodasLasPublicaciones());
        modeloVista.addObject("publicacionesArchivadas", servicioPublicacion.obtenerPublicacionesArchivadas());
        return modeloVista;
    }
}
