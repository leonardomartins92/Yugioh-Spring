package com.stefanini.yugioh.repository;

import com.stefanini.yugioh.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadorRepository extends JpaRepository <Jogador, Long> {
}
