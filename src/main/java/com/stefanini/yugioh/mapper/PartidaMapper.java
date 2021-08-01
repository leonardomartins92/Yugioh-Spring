package com.stefanini.yugioh.mapper;

import com.stefanini.yugioh.dto.PartidaDto;
import com.stefanini.yugioh.model.Partida;
import org.modelmapper.ModelMapper;

public class PartidaMapper {

    private PartidaMapper(){};
    private static PartidaMapper instance = new PartidaMapper();
    public static PartidaMapper getInstance() { return instance;}

    public PartidaDto toDTO(Partida partida) {
        ModelMapper modelMapper = new ModelMapper();
        PartidaDto partidaDTO = modelMapper.map(partida, PartidaDto.class);
        return partidaDTO;
    }

    public Partida toModel(PartidaDto partidaDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(partidaDTO, Partida.class);
    }
}
