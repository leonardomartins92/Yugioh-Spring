package com.stefanini.yugioh.service;

import com.stefanini.yugioh.builder.JogadaBuilder;
import com.stefanini.yugioh.dto.JogadaDto;
import com.stefanini.yugioh.mapper.JogadaMapper;
import com.stefanini.yugioh.model.Jogada;
import com.stefanini.yugioh.repository.JogadaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Jogada Service")
@ExtendWith(MockitoExtension.class)
class JogadaServiceTest {

    private static final long ID_INVALIDO_JOGADA = 2L;

    @Mock
    private JogadaRepository jogadaRepository;

    private JogadaMapper jogadaMapper = JogadaMapper.getInstance();

    @InjectMocks
    private JogadaService jogadaService;

    @Test
    @DisplayName("Jogada deve ser Salva")
    void saveCard() {
        // given
        JogadaDto jogadaDto = JogadaBuilder.builder().build().jogadaDto();
        Jogada jogadaParaSalvar = jogadaMapper.toModel(jogadaDto);

        // when
        when(jogadaRepository.save(jogadaParaSalvar)).thenReturn(jogadaParaSalvar);

        //then
        Jogada jogadaSalva = jogadaService.save(jogadaParaSalvar);

        assertEquals(jogadaParaSalvar, jogadaSalva);

    }

    @Test
    @DisplayName("Deve retornar lista de jogadas")
    void getAll(){
        //given
        JogadaDto jogadaDto = JogadaBuilder.builder().build().jogadaDto();
        Jogada jogadaEsperada = jogadaMapper.toModel(jogadaDto);

        //when
        when(jogadaRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(jogadaEsperada)));

        //then
        Page<Jogada> jogadas = jogadaService.getAll(PageRequest.of(1,1));

        assertEquals(jogadaEsperada, jogadas.toList().get(0));
    }


    @Test
    @DisplayName("Deve retornar uma jogada")
    void getOne(){
        //given
        JogadaDto jogadaDto = JogadaBuilder.builder().build().jogadaDto();
        Jogada jogadaEsperada = jogadaMapper.toModel(jogadaDto);

        //when
        when(jogadaRepository.findById(1L)).thenReturn(Optional.of(jogadaEsperada));

        //then
        Optional<Jogada> jogada = jogadaService.getOne(1L);

        assertEquals(jogadaEsperada, jogada.get());
    }

    @Test
    @DisplayName("Deve retornar Optional null")
    void getOneWithInvalidId(){

        //when
        when(jogadaRepository.findById(ID_INVALIDO_JOGADA)).thenReturn(Optional.empty());

        //then
        Optional<Jogada> jogada = jogadaService.getOne(ID_INVALIDO_JOGADA);

        assertEquals(Optional.empty(), jogada);
    }

    @Test
    @DisplayName("Deve deletar uma jogada")
    void deleteOne(){
        //given
        JogadaDto jogadaDto = JogadaBuilder.builder().build().jogadaDto();
        Jogada jogadaParaDeletar = jogadaMapper.toModel(jogadaDto);

        //when
        doNothing().when(jogadaRepository).deleteById(jogadaParaDeletar.getId());

        //then
        jogadaService.delete(jogadaParaDeletar.getId());
        verify(jogadaRepository, times(1)).deleteById(jogadaParaDeletar.getId());
    }


}
