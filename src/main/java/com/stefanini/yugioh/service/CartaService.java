package com.stefanini.yugioh.service;

import com.stefanini.yugioh.model.Carta;
import com.stefanini.yugioh.repository.CartaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CartaService implements IService<Carta> {
    
    private final CartaRepository cartaRepository;

    public Page<Carta> getAll(Pageable pageable){
        return cartaRepository.findAll(pageable);
    }

    public Optional<Carta> getOne(Long id){
        return cartaRepository.findById(id);
    }

    public Carta save(Carta carta){
        return cartaRepository.save(carta);
    }

    public void delete(Long id){
        cartaRepository.deleteById(id);
    }
}
