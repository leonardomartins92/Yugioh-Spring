package com.stefanini.yugioh.mapper;

import com.stefanini.yugioh.dto.CartaDto;
import com.stefanini.yugioh.model.Carta;
import org.modelmapper.ModelMapper;

public class CartaMapper {

    private CartaMapper(){};
    private static CartaMapper instance = new CartaMapper();
    public static CartaMapper getInstance() { return instance;}

    public CartaDto toDTO(Carta carta) {
        ModelMapper modelMapper = new ModelMapper();
        CartaDto cartaDTO = modelMapper.map(carta, CartaDto.class);
        return cartaDTO;
    }

    public Carta toModel(CartaDto clienteDTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(clienteDTO, Carta.class);
    }
}
