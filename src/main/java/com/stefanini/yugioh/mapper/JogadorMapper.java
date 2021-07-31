package com.stefanini.yugioh.mapper;

import com.stefanini.yugioh.dto.JogadorDto;
import com.stefanini.yugioh.model.Jogador;
import org.modelmapper.ModelMapper;

public class JogadorMapper {

    private JogadorMapper(){};
    private static JogadorMapper instance = new JogadorMapper();
    public static JogadorMapper getInstance() { return instance;}

    public JogadorDto toDTO(Jogador jogador) {
        ModelMapper modelMapper = new ModelMapper();
        JogadorDto jogadorDTO = modelMapper.map(jogador, JogadorDto.class);
        return jogadorDTO;
    }

    public Jogador toModel(JogadorDto jogadorDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(jogadorDTO, Jogador.class);
    }
}
