package com.stefanini.yugioh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JogadaDto extends RepresentationModel {

    private Long id;

    @JsonProperty("cardId")
    private Long cartaId;

    @JsonProperty("playerId")
    private Long jogadorId;

    private Long partidaId;
}
