package com.stefanini.yugioh.resource;

import com.stefanini.yugioh.controller.JogadorController;
import com.stefanini.yugioh.model.Jogador;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

@Service
public class JogadorResource {

    public Link linkToJogador(Jogador jogador) {

        Link link = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(JogadorController.class)
                        .getOne(jogador.getId()))
                .withSelfRel();
       return link;

    }
}
