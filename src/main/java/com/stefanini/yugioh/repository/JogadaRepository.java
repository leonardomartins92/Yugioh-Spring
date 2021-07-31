package com.stefanini.yugioh.repository;

import com.stefanini.yugioh.model.Jogada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadaRepository extends JpaRepository<Jogada, Long> {
}
