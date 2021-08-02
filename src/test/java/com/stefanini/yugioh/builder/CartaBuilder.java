package com.stefanini.yugioh.builder;

import com.stefanini.yugioh.model.Carta;
import lombok.Builder;

@Builder
public class CartaBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String nome = "Nome";

    @Builder.Default
    private String detalhes = "Detalhes";

    @Builder.Default
    private Integer atributoAtaque = 50;

    @Builder.Default
    private Integer atributoDefesa = 10;

    @Builder.Default
    private String imagemSrc = "c://";

    public Carta cartaModel(){
        return new Carta(id,
                nome,
                detalhes,
                atributoAtaque,
                atributoDefesa,
                imagemSrc);
    }

}
