package com.stefanini.yugioh.controller;

import com.stefanini.yugioh.dto.JogadorDto;
import com.stefanini.yugioh.mapper.JogadorMapper;
import com.stefanini.yugioh.model.Jogador;
import com.stefanini.yugioh.resource.JogadorResource;
import com.stefanini.yugioh.service.JogadorService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/players")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class JogadorController {

    private final JogadorService jogadorService;
    private final JogadorResource jogadorResource;
    private final JogadorMapper mapper = JogadorMapper.getInstance();

    @GetMapping
    public ResponseEntity<List<JogadorDto>> getAll(Pageable pageable){
         return ResponseEntity.status(HttpStatus.OK).body(
                 jogadorService.getAll(pageable).stream()
                         .map(mapper::toDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<JogadorDto>> getOne(@PathVariable Long id){
        Optional<Jogador> jogador = jogadorService.getOne(id);
        if(jogador.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Link link = jogadorResource.linkToJogador(jogador.get());
        JogadorDto jogadorDto = mapper.toDTO(jogador.get());
        EntityModel<JogadorDto> model = EntityModel.of(jogadorDto);
        model.add(link);

        return ResponseEntity.status(HttpStatus.OK)
                .body(model);
    }

    @PostMapping
    public ResponseEntity<JogadorDto> save(@RequestBody @Valid JogadorDto jogadorDto){
       Jogador jogadorSalva =  jogadorService.save(mapper.toModel(jogadorDto));
       return ResponseEntity.status(HttpStatus.CREATED)
               .body(mapper.toDTO(jogadorSalva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JogadorDto> update(@RequestBody @Valid JogadorDto jogadorDto, @PathVariable Long id){
       Optional<Jogador> jogadorSalva = jogadorService.getOne(id);
       if(jogadorSalva.isEmpty()){
           return ResponseEntity.badRequest().build();
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
            return ResponseEntity.badRequest().build();
        }
        jogadorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
