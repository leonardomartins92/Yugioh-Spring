package com.stefanini.yugioh.service;

import com.stefanini.yugioh.builder.CartaBuilder;
import com.stefanini.yugioh.dto.CartaDto;
import com.stefanini.yugioh.mapper.CartaMapper;
import com.stefanini.yugioh.model.Carta;
import com.stefanini.yugioh.repository.CartaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@DisplayName("Carta Service")
@ExtendWith(MockitoExtension.class)
class CartaServiceTest {

    private static final long ID_INVALIDO_CARTA = 2L;

    @Mock
    private CartaRepository cartaRepository;

    @InjectMocks
    private CartaService cartaService;

    private CartaMapper cartaMapper = CartaMapper.getInstance();

    @Test
    @DisplayName("Carta deve ser Salva")
    void saveCard() {
        // given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        Carta cartaParaSalvar = cartaMapper.toModel(cartaDto);

        // when
        when(cartaRepository.save(cartaParaSalvar)).thenReturn(cartaParaSalvar);

        //then
        Carta cartaSalva = cartaService.save(cartaParaSalvar);

        assertEquals(cartaParaSalvar, cartaSalva);

    }

    @Test
    @DisplayName("Deve retornar lista de cartas")
    void getAll(){
        //given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        Carta cartaEsperada = cartaMapper.toModel(cartaDto);

        //when
        when(cartaRepository.findAll()).thenReturn(List.of(cartaEsperada));

        //then
        List<Carta> cartas = cartaService.getAll();

        assertEquals(cartaEsperada, cartas.get(0));
    }

    @Test
    @DisplayName("Deve retornar lista vazia")
    void getEmptyList(){

        //when
        when(cartaRepository.findAll()).thenReturn(List.of());

        //then
        List<Carta> cartas = cartaService.getAll();

        assertEquals(List.of(), cartas);
    }

    @Test
    @DisplayName("Deve retornar uma carta")
    void getOne(){
        //given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        Carta cartaEsperada = cartaMapper.toModel(cartaDto);

        //when
        when(cartaRepository.findById(1L)).thenReturn(Optional.of(cartaEsperada));

        //then
        Optional<Carta> carta = cartaService.getOne(1L);

        assertEquals(cartaEsperada, carta.get());
    }

    @Test
    @DisplayName("Deve retornar Optional null")
    void getOneWithInvalidId(){

        //when
        when(cartaRepository.findById(ID_INVALIDO_CARTA)).thenReturn(Optional.empty());

        //then
        Optional<Carta> carta = cartaService.getOne(ID_INVALIDO_CARTA);

        assertEquals(Optional.empty(), carta);
    }

    @Test
    @DisplayName("Deve deletar uma carta")
    void deleteOne(){
        //given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        Carta cartaParaDeletar = cartaMapper.toModel(cartaDto);

        //when
        doNothing().when(cartaRepository).deleteById(cartaParaDeletar.getId());

        //then
        cartaService.delete(cartaParaDeletar.getId());
        verify(cartaRepository, times(1)).deleteById(cartaParaDeletar.getId());
    }


}
