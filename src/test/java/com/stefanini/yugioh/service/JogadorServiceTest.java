package com.stefanini.yugioh.service;

import com.stefanini.yugioh.builder.JogadorBuilder;
import com.stefanini.yugioh.dto.JogadorDto;
import com.stefanini.yugioh.mapper.JogadorMapper;
import com.stefanini.yugioh.model.Jogador;
import com.stefanini.yugioh.repository.JogadorRepository;
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
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        Jogador jogadorParaSalvar = jogadorMapper.toModel(jogadorDto);

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
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        Jogador jogadorEsperada = jogadorMapper.toModel(jogadorDto);

        //when
        when(jogadorRepository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(jogadorEsperada)));

        //then
        Page<Jogador> jogadors = jogadorService.getAll(PageRequest.of(1, 1));

        assertEquals(jogadorEsperada, jogadors.toList().get(0));
    }

    @Test
    @DisplayName("Deve retornar uma jogador")
    void getOne(){
        //given
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        Jogador jogadorEsperada = jogadorMapper.toModel(jogadorDto);

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
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        Jogador jogadorParaDeletar = jogadorMapper.toModel(jogadorDto);

        //when
        doNothing().when(jogadorRepository).deleteById(jogadorParaDeletar.getId());

        //then
        jogadorService.delete(jogadorParaDeletar.getId());
        verify(jogadorRepository, times(1)).deleteById(jogadorParaDeletar.getId());
    }


}
