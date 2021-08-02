package com.stefanini.yugioh.builder;

import com.stefanini.yugioh.dto.JogadaDto;
import com.stefanini.yugioh.dto.JogadorDto;
import com.stefanini.yugioh.dto.PartidaDto;
import lombok.Builder;

import java.util.List;

@Builder
public class PartidaBuilder {
    @Builder.Default
    private Long id = 1L;

    private List<JogadaDto> jogadas;

    private List<JogadorDto> jogador;

    public PartidaDto partidaDTO(){
        return new PartidaDto();
    }

}
