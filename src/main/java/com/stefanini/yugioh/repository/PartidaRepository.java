package com.stefanini.yugioh.repository;

import com.stefanini.yugioh.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
}
