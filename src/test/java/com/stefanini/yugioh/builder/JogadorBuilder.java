package com.stefanini.yugioh.builder;

import com.stefanini.yugioh.model.Jogador;
import com.stefanini.yugioh.model.Partida;
import lombok.Builder;

@Builder
public class JogadorBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String nome = "Nome";

    @Builder.Default
    private String cpf = "51628029056";

    @Builder.Default
    private String email = "email@gmail.com";

    @Builder.Default
    private Integer partidasJogadas = 10;

    @Builder.Default
    private Integer partidasGanhas = 2;

    public Jogador jogadorModel(){
        return new Jogador(id,
                cpf,
                nome,
                email,
                partidasGanhas,
                partidasJogadas);
    }

}
