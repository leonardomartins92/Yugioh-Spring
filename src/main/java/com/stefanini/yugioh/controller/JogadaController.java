package com.stefanini.yugioh.controller;

import com.stefanini.yugioh.dto.JogadaDto;
import com.stefanini.yugioh.mapper.JogadaMapper;
import com.stefanini.yugioh.model.Jogada;
import com.stefanini.yugioh.service.JogadaService;
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
@RequestMapping("/api/v1/games")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class JogadaController {

    private final JogadaService jogadaService;
    private final JogadaMapper mapper = JogadaMapper.getInstance();

    @GetMapping
    public ResponseEntity getAll(){
        List<Jogada> jogadas = jogadaService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                jogadas.stream().map(mapper::toDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity getOne(@PathVariable Long id){
        Optional<Jogada> jogada = jogadaService.getOne(id);
        if(jogada.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Card not found");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(mapper.toDTO(jogada.get()));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody @Valid JogadaDto jogadaDto){
       Jogada jogadaSalva =  jogadaService.save(mapper.toModel(jogadaDto));
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(mapper.toDTO(jogadaSalva));
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody @Valid JogadaDto jogadaDto, @PathVariable Long id){
       Optional<Jogada> jogadaSalva = jogadaService.getOne(id);
       if(jogadaSalva.isEmpty()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card not Found");
       }
        jogadaDto.setId(id);
        Jogada jogadaAtualizada =  jogadaService.save(mapper.toModel(jogadaDto));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDTO(jogadaAtualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Optional<Jogada> jogadaSalva = jogadaService.getOne(id);
        if(jogadaSalva.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Card not Found");
        }
        jogadaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
