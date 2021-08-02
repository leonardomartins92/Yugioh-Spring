package com.stefanini.yugioh.controller;

import com.stefanini.yugioh.dto.JogadorDto;
import com.stefanini.yugioh.mapper.JogadorMapper;
import com.stefanini.yugioh.model.Jogador;
import com.stefanini.yugioh.service.JogadorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/players")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class JogadorController {

    private final JogadorService jogadorService;
    private final JogadorMapper mapper = JogadorMapper.getInstance();

    @GetMapping
    public ResponseEntity getAll(){
        List<Jogador> jogadors = jogadorService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                jogadors.stream().map(mapper::toDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id){
        Optional<Jogador> jogador = jogadorService.getOne(id);
        if(jogador.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Card not found");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.toDTO(jogador.get()));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid JogadorDto jogadorDto){
       Jogador jogadorSalva =  jogadorService.save(mapper.toModel(jogadorDto));
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(mapper.toDTO(jogadorSalva));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody @Valid JogadorDto jogadorDto, @PathVariable Long id){
       Optional<Jogador> jogadorSalva = jogadorService.getOne(id);
       if(jogadorSalva.isEmpty()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Jogador not Found");
       }
        jogadorDto.setId(id);
        Jogador jogadorAtualizada =  jogadorService.save(mapper.toModel(jogadorDto));
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.toDTO(jogadorAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Optional<Jogador> jogadorSalva = jogadorService.getOne(id);
        if(jogadorSalva.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card not Found");
        }
        jogadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
