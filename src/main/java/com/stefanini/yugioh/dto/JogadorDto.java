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

    @CPF
    @NotBlank
    private String cpf;

    @NotBlank
    @Size(min = 3, max = 45)
    private String nome;

    @Email
    private String email;

}
