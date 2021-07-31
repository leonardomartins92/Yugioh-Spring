package com.stefanini.yugioh.mapper;

import com.stefanini.yugioh.dto.JogadaDto;
import com.stefanini.yugioh.model.Jogada;
import org.modelmapper.ModelMapper;

public class JogadaMapper {

    private JogadaMapper(){};
    private static JogadaMapper instance = new JogadaMapper();
    public static JogadaMapper getInstance() { return instance;}

    public JogadaDto toDTO(Jogada jogada) {
        ModelMapper modelMapper = new ModelMapper();
        JogadaDto jogadaDTO = modelMapper.map(jogada, JogadaDto.class);
        return jogadaDTO;
    }

    public Jogada toModel(JogadaDto jogadaDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(jogadaDTO, Jogada.class);
    }
}
