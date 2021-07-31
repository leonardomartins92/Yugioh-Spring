package com.stefanini.yugioh.repository;

import com.stefanini.yugioh.model.Carta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaRepository extends JpaRepository<Carta, Long> {
}
