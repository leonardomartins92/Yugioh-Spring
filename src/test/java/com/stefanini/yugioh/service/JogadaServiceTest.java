package com.stefanini.yugioh.service;

import com.stefanini.yugioh.builder.JogadaBuilder;
import com.stefanini.yugioh.mapper.JogadaMapper;
import com.stefanini.yugioh.model.Jogada;
import com.stefanini.yugioh.repository.JogadaRepository;
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
        Jogada jogadaParaSalvar = JogadaBuilder.builder().build().jogadaModel();

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
        Jogada jogadaEsperada = JogadaBuilder.builder().build().jogadaModel();

        //when
        when(jogadaRepository.findAll()).thenReturn(List.of(jogadaEsperada));

        //then
        List<Jogada> jogadas = jogadaService.getAll();

        assertEquals(jogadaEsperada, jogadas.get(0));
    }

    @Test
    @DisplayName("Deve retornar lista vazia")
    void getEmptyList(){

        //when
        when(jogadaRepository.findAll()).thenReturn(List.of());

        //then
        List<Jogada> jogadas = jogadaService.getAll();

        assertEquals(List.of(), jogadas);
    }

    @Test
    @DisplayName("Deve retornar uma jogada")
    void getOne(){
        //given
        Jogada jogadaEsperada = JogadaBuilder.builder().build().jogadaModel();

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
        Jogada jogadaParaDeletar = JogadaBuilder.builder().build().jogadaModel();

        //when
        doNothing().when(jogadaRepository).deleteById(jogadaParaDeletar.getId());

        //then
        jogadaService.delete(jogadaParaDeletar.getId());
        verify(jogadaRepository, times(1)).deleteById(jogadaParaDeletar.getId());
    }


}
