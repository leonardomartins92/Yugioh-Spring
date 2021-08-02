package com.stefanini.yugioh.service;

import com.stefanini.yugioh.builder.PartidaBuilder;
import com.stefanini.yugioh.dto.PartidaDto;
import com.stefanini.yugioh.mapper.PartidaMapper;
import com.stefanini.yugioh.model.Partida;
import com.stefanini.yugioh.repository.PartidaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Partida Service")
@ExtendWith(MockitoExtension.class)
class PartidaServiceTest {

    private static final long ID_INVALIDO_PARTIDA = 2L;

    @Mock
    private PartidaRepository partidaRepository;

    private final PartidaMapper partidaMapper = PartidaMapper.getInstance();

    @InjectMocks
    private PartidaService partidaService;

    @Test
    @DisplayName("Partida deve ser Salva")
    void saveCard() {
        // given
        PartidaDto partidaDto = PartidaBuilder.builder().build().partidaDTO();
        Partida partidaParaSalvar = partidaMapper.toModel(partidaDto);

        // when
        when(partidaRepository.save(partidaParaSalvar)).thenReturn(partidaParaSalvar);

        //then
        Partida partidaSalva = partidaService.save(partidaParaSalvar);

        assertEquals(partidaParaSalvar, partidaSalva);

    }

    @Test
    @DisplayName("Deve retornar lista de partidas")
    void getAll(){
        //given
        PartidaDto partidaDto = PartidaBuilder.builder().build().partidaDTO();
        Partida partidaEsperada = partidaMapper.toModel(partidaDto);

        //when
        when(partidaRepository.findAll()).thenReturn(List.of(partidaEsperada));

        //then
        List<Partida> partidas = partidaService.getAll();

        assertEquals(partidaEsperada, partidas.get(0));
    }

    @Test
    @DisplayName("Deve retornar lista vazia")
    void getEmptyList(){

        //when
        when(partidaRepository.findAll()).thenReturn(List.of());

        //then
        List<Partida> partidas = partidaService.getAll();

        assertEquals(List.of(), partidas);
    }

    @Test
    @DisplayName("Deve retornar uma partida")
    void getOne(){
        //given
        PartidaDto partidaDto = PartidaBuilder.builder().build().partidaDTO();
        Partida partidaEsperada = partidaMapper.toModel(partidaDto);

        //when
        when(partidaRepository.findById(1L)).thenReturn(Optional.of(partidaEsperada));

        //then
        Optional<Partida> partida = partidaService.getOne(1L);

        assertEquals(partidaEsperada, partida.get());
    }

    @Test
    @DisplayName("Deve retornar Optional null")
    void getOneWithInvalidId(){

        //when
        when(partidaRepository.findById(ID_INVALIDO_PARTIDA)).thenReturn(Optional.empty());

        //then
        Optional<Partida> partida = partidaService.getOne(ID_INVALIDO_PARTIDA);

        assertEquals(Optional.empty(), partida);
    }

    @Test
    @DisplayName("Deve deletar uma partida")
    void deleteOne(){
        //given
        PartidaDto partidaDto = PartidaBuilder.builder().build().partidaDTO();
        Partida partidaParaDeletar = partidaMapper.toModel(partidaDto);

        //when
        doNothing().when(partidaRepository).deleteById(partidaParaDeletar.getId());

        //then
        partidaService.delete(partidaParaDeletar.getId());
        verify(partidaRepository, times(1)).deleteById(partidaParaDeletar.getId());
    }


}
