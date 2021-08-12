package com.stefanini.yugioh.service;

import com.stefanini.yugioh.model.Jogador;
import com.stefanini.yugioh.repository.JogadorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class JogadorService implements IService<Jogador> {

    private final JogadorRepository jogadorRepository;

    public Page<Jogador> getAll(Pageable pageable){
        return jogadorRepository.findAll(pageable);
    }

    public Optional<Jogador> getOne(Long id){
        return jogadorRepository.findById(id);
    }

    public Jogador save(Jogador jogador){
        return jogadorRepository.save(jogador);
    }

    public void delete(Long id){
        jogadorRepository.deleteById(id);
    }
}
