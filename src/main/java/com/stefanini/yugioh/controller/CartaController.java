package com.stefanini.yugioh.controller;

import com.stefanini.yugioh.dto.CartaDto;
import com.stefanini.yugioh.mapper.CartaMapper;
import com.stefanini.yugioh.model.Carta;
import com.stefanini.yugioh.model.Jogador;
import com.stefanini.yugioh.service.CartaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/cards")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CartaController {

    private final CartaService cartaService;
    private final CartaMapper mapper = CartaMapper.getInstance();

    @GetMapping
    public ResponseEntity<List<CartaDto>> getAll(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(
                cartaService.getAll(pageable).stream()
                        .map(mapper::toDTO)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaDto> getOne(@PathVariable Long id){
        Optional<Carta> carta = cartaService.getOne(id);
        if(carta.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.toDTO(carta.get()));
    }

    @PostMapping
     public ResponseEntity<CartaDto> save(@RequestBody @Valid CartaDto cartaDto){
        Carta cartaSalva =  cartaService.save(mapper.toModel(cartaDto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDTO(cartaSalva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartaDto> update(@RequestBody @Valid CartaDto cartaDto, @PathVariable Long id){
       Optional<Carta> cartaSalva = cartaService.getOne(id);
       if(cartaSalva.isEmpty()){
           return ResponseEntity.badRequest().build();
       }
        cartaDto.setId(id);
        Carta cartaAtualizada =  cartaService.save(mapper.toModel(cartaDto));
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.toDTO(cartaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Optional<Carta> cartaSalva = cartaService.getOne(id);
        if(cartaSalva.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        cartaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
