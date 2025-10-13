package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioSombrero;
import com.tallerwebi.dominio.TipoSombrero;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ControladorSombreroTest {

    @Mock
    private ServicioSombrero servicioSombrero;

    @InjectMocks
    private ControladorSombrero controladorSombrero;

    private SombreroViewModel sombreroDTO;

    @BeforeEach
    public void setUp() {
        sombreroDTO = new SombreroViewModel();
        sombreroDTO.setId(1L);
        sombreroDTO.setTipo(TipoSombrero.BEANIE);
        sombreroDTO.setValorProduccion(100.0);
    }
}
