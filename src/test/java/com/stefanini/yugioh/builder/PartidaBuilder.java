package com.stefanini.yugioh.builder;

import com.stefanini.yugioh.model.Partida;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class PartidaBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private LocalDate data = LocalDate.now();

    public Partida partidaModel(){
        return new Partida();
    }

}
