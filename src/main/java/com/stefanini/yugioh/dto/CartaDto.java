package com.stefanini.yugioh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartaDto {

    private Long id;

    @NotBlank(message = "Please add card name")
    @Size(min = 3, max=45, message = "Name size must be between 3 and 45")
    private String nome;

    @Size(max=256)
    private String detalhes;

    @NotNull(message = "Please add attack attribute")
    @Min(value = 0, message = "The minimum value is 0")
    @Max(value = 100, message = "The maximum value is 100")
    private Integer atributoAtaque;

    @NotNull(message = "Please add defense attribute")
    @Min(value = 0, message = "The minimum value is 0")
    @Max(value = 100, message = "The maximum value is 100")
    private Integer atributoDefesa;

    private String imagemSrc;

}
