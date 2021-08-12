package com.stefanini.yugioh.service;

import com.stefanini.yugioh.model.Jogada;
import com.stefanini.yugioh.repository.JogadaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class JogadaService implements IService<Jogada> {
    
    private final JogadaRepository jogadaRepository;

    public Page<Jogada> getAll(Pageable pageable){
        return jogadaRepository.findAll(pageable);
    }

    public Optional<Jogada> getOne(Long id){
        return jogadaRepository.findById(id);
    }

    public Jogada save(Jogada jogada){
        return jogadaRepository.save(jogada);
    }

    public void delete(Long id){
        jogadaRepository.deleteById(id);
    }
}
