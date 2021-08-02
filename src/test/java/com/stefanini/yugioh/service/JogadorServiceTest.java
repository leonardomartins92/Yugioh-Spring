package com.stefanini.yugioh.service;

import com.stefanini.yugioh.builder.JogadorBuilder;
import com.stefanini.yugioh.mapper.JogadorMapper;
import com.stefanini.yugioh.model.Jogador;
import com.stefanini.yugioh.repository.JogadorRepository;
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

@DisplayName("Jogador Service")
@ExtendWith(MockitoExtension.class)
class JogadorServiceTest {

    private static final long ID_INVALIDO_JOGADOR = 2L;

    @Mock
    private JogadorRepository jogadorRepository;

    private JogadorMapper jogadorMapper = JogadorMapper.getInstance();

    @InjectMocks
    private JogadorService jogadorService;

    @Test
    @DisplayName("Jogador deve ser Salva")
    void saveCard() {
        // given
        Jogador jogadorParaSalvar = JogadorBuilder.builder().build().jogadorModel();

        // when
        when(jogadorRepository.save(jogadorParaSalvar)).thenReturn(jogadorParaSalvar);

        //then
        Jogador jogadorSalva = jogadorService.save(jogadorParaSalvar);

        assertEquals(jogadorParaSalvar, jogadorSalva);

    }

    @Test
    @DisplayName("Deve retornar lista de jogadors")
    void getAll(){
        //given
        Jogador jogadorEsperada = JogadorBuilder.builder().build().jogadorModel();

        //when
        when(jogadorRepository.findAll()).thenReturn(List.of(jogadorEsperada));

        //then
        List<Jogador> jogadors = jogadorService.getAll();

        assertEquals(jogadorEsperada, jogadors.get(0));
    }

    @Test
    @DisplayName("Deve retornar lista vazia")
    void getEmptyList(){

        //when
        when(jogadorRepository.findAll()).thenReturn(List.of());

        //then
        List<Jogador> jogadors = jogadorService.getAll();

        assertEquals(List.of(), jogadors);
    }

    @Test
    @DisplayName("Deve retornar uma jogador")
    void getOne(){
        //given
        Jogador jogadorEsperada = JogadorBuilder.builder().build().jogadorModel();

        //when
        when(jogadorRepository.findById(1L)).thenReturn(Optional.of(jogadorEsperada));

        //then
        Optional<Jogador> jogador = jogadorService.getOne(1L);

        assertEquals(jogadorEsperada, jogador.get());
    }

    @Test
    @DisplayName("Deve retornar Optional null")
    void getOneWithInvalidId(){

        //when
        when(jogadorRepository.findById(ID_INVALIDO_JOGADOR)).thenReturn(Optional.empty());

        //then
        Optional<Jogador> jogador = jogadorService.getOne(ID_INVALIDO_JOGADOR);

        assertEquals(Optional.empty(), jogador);
    }

    @Test
    @DisplayName("Deve deletar uma jogador")
    void deleteOne(){
        //given
        Jogador jogadorParaDeletar = JogadorBuilder.builder().build().jogadorModel();

        //when
        doNothing().when(jogadorRepository).deleteById(jogadorParaDeletar.getId());

        //then
        jogadorService.delete(jogadorParaDeletar.getId());
        verify(jogadorRepository, times(1)).deleteById(jogadorParaDeletar.getId());
    }


}
