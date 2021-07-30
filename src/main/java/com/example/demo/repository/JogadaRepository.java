package com.example.demo.repository;

import com.example.demo.model.Jogada;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadaRepository extends JpaRepository<Jogada, Long> {
}
