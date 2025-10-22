package com.tallerwebi.dominio;

public interface RepositorioParticipante {
    Participante guardar(Participante participante);

    Participante buscarPorIdInscripcion(String number);
}
