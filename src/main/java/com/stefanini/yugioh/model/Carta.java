package com.stefanini.yugioh.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String detalhes;

    @Column(nullable = false)
    private Integer atributoAtaque;

    @Column(nullable = false)
    private Integer atributoDefesa;

    private String imagemSrc;

}
