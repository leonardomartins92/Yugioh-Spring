package com.stefanini.yugioh.builder;

import com.stefanini.yugioh.dto.JogadaDto;
import com.stefanini.yugioh.model.Carta;
import com.stefanini.yugioh.model.Jogada;
import com.stefanini.yugioh.model.Jogador;
import com.stefanini.yugioh.model.Partida;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class JogadaBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private Long cartaId = 1L;

    @Builder.Default
    private Long jogadorId = 1L;

    @Builder.Default
    private Long partidaId = 1L;

    public JogadaDto jogadaDto(){
        return new JogadaDto();
    }

}
