package com.stefanini.yugioh.builder;

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
    private Carta carta = new Carta();

    @Builder.Default
    private Jogador jogador = new Jogador();

    @Builder.Default
    private Partida partida = new Partida();

    public Jogada jogadaModel(){
        return new Jogada();
    }

}
