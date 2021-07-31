package com.stefanini.yugioh.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Partida{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    @ManyToMany
    private List<Jogador> jogador;

    public Partida(Long id, List<Jogador> jogador) {
        this.id = id;
        this.jogador = jogador;
        this.data = LocalDate.now();
    }
}
