package com.stefanini.yugioh.service;

import com.stefanini.yugioh.model.Carta;
import com.stefanini.yugioh.repository.CartaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CartaService implements IService<Carta> {
    
    private final CartaRepository cartaRepository;

    public List<Carta> getAll(){
        return cartaRepository.findAll();
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
