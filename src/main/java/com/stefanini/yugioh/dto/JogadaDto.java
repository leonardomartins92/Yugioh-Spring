package com.stefanini.yugioh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JogadaDto {

    private Long id;

    private Long cartaId;

    private Long jogadorId;

    private Long partidaId;
}
