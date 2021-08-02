package com.stefanini.yugioh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JogadorDto {

    private Long id;

    @CPF(message = "Invalid CPF")
    @NotBlank
    private String cpf;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 45, message = "Name size must be between 3 and 45")
    private String nome;

    @Email(message = "Please, insert a valid email")
    private String email;

    private Integer partidasJogadas;
    private Integer partidasGanhas;

}
