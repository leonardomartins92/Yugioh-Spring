package com.stefanini.yugioh.service;

import com.stefanini.yugioh.model.Partida;
import com.stefanini.yugioh.repository.PartidaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PartidaService implements IService<Partida>{
    
    private final PartidaRepository partidaRepository;

    public Page<Partida> getAll(Pageable pageable){
        return partidaRepository.findAll(pageable);
    }

    public Optional<Partida> getOne(Long id){
        return partidaRepository.findById(id);
    }

    public Partida save(Partida partida){
        return partidaRepository.save(partida);
    }

    public void delete(Long id){
        partidaRepository.deleteById(id);
    }
}
