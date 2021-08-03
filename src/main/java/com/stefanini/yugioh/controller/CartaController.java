package com.stefanini.yugioh.controller;

import com.stefanini.yugioh.dto.CartaDto;
import com.stefanini.yugioh.mapper.CartaMapper;
import com.stefanini.yugioh.model.Carta;
import com.stefanini.yugioh.service.CartaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/cards")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CartaController {

    private final CartaService cartaService;
    private final CartaMapper mapper = CartaMapper.getInstance();

    @GetMapping
    public ResponseEntity getAll(){
        List<Carta> cartas = cartaService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                cartas.stream().map(mapper::toDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id){
        Optional<Carta> carta = cartaService.getOne(id);
        if(carta.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Card not found");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.toDTO(carta.get()));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid CartaDto cartaDto){
       Carta cartaSalva =  cartaService.save(mapper.toModel(cartaDto));
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(mapper.toDTO(cartaSalva));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody @Valid CartaDto cartaDto, @PathVariable Long id){
       Optional<Carta> cartaSalva = cartaService.getOne(id);
       if(cartaSalva.isEmpty()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card not Found");
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card not Found");
        }
        cartaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
