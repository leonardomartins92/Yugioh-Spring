package com.stefanini.yugioh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartidaDto {

    private Long id;

    @JsonProperty("plays")
    private List<JogadaDto> jogadas;

    @JsonProperty("players")
    private List<JogadorDto> jogador;


}
