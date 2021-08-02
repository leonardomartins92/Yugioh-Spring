package com.stefanini.yugioh.controller;

import com.stefanini.yugioh.builder.JogadaBuilder;
import com.stefanini.yugioh.builder.JogadorBuilder;
import com.stefanini.yugioh.builder.PartidaBuilder;
import com.stefanini.yugioh.dto.JogadaDto;
import com.stefanini.yugioh.dto.JogadorDto;
import com.stefanini.yugioh.dto.PartidaDto;
import com.stefanini.yugioh.mapper.JogadaMapper;
import com.stefanini.yugioh.mapper.PartidaMapper;
import com.stefanini.yugioh.model.Jogada;
import com.stefanini.yugioh.model.Partida;
import com.stefanini.yugioh.service.JogadaService;
import com.stefanini.yugioh.service.PartidaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;

import static com.stefanini.yugioh.utils.JsonConvertionUtils.asJsonString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PartidaControllerTest {

    private static final String API_URL_PATH = "/api/v1/games";
    private static final long VALID_ID= 1L;
    private static final long INVALID_ID= 2L;

    private MockMvc mockMvc;

    @Mock
    private PartidaService partidaService;

    @Mock
    private JogadaService jogadaService;

    private final PartidaMapper partidaMapper = PartidaMapper.getInstance();
    private final JogadaMapper jogadaMapper = JogadaMapper.getInstance();

    @InjectMocks
    private PartidaController partidaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(partidaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    @DisplayName("Salva nova Partida")
    void save() throws Exception {
        // given
        PartidaDto partidaDto = PartidaBuilder.builder().build().partidaDTO();
        JogadaDto jogadaDto = JogadaBuilder.builder().build().jogadaDto();
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        partidaDto.setJogadas(List.of(jogadaDto));
        partidaDto.setJogador(List.of(jogadorDto));

        Partida partida = partidaMapper.toModel(partidaDto);
        Jogada jogada = jogadaMapper.toModel(jogadaDto);

        // when
        when(partidaService.save(partida)).thenReturn(partida);
        when(jogadaService.save(jogada)).thenReturn(jogada);

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(partidaDto)))
                .andExpect(status().isCreated());
    }


}
