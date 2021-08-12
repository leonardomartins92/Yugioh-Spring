package com.stefanini.yugioh.controller;

import com.stefanini.yugioh.builder.JogadorBuilder;
import com.stefanini.yugioh.dto.JogadorDto;
import com.stefanini.yugioh.mapper.JogadorMapper;
import com.stefanini.yugioh.model.Carta;
import com.stefanini.yugioh.model.Jogador;
import com.stefanini.yugioh.service.JogadorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;
import java.util.Optional;

import static com.stefanini.yugioh.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class JogadorControllerTest {

    private static final String API_URL_PATH = "/api/v1/players";
    private static final long VALID_ID= 1L;
    private static final long INVALID_ID= 2L;

    private MockMvc mockMvc;

    @Mock
    private JogadorService jogadorService;

    private JogadorMapper jogadorMapper = JogadorMapper.getInstance();

    @InjectMocks
    private JogadorController jogadorController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(jogadorController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    @DisplayName("Salva novo Jogador")
    void save() throws Exception {
        // given
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        Jogador jogador = jogadorMapper.toModel(jogadorDto);

        // when
        doReturn(jogador).when(jogadorService).save(jogador);
        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jogadorDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is(jogadorDto.getNome())));
    }

    @Test
    @DisplayName("Nome muito pequeno para novo Jogador")
    void tooSmallName() throws Exception {
        // given
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        jogadorDto.setNome("ab");

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jogadorDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Nome muito grande para novo Jogador")
    void tooBigName() throws Exception {
        // given
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        jogadorDto.setNome("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jogadorDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("CPF invalido não cria novo Jogador")
    void invalidCpf() throws Exception {
        // given
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        jogadorDto.setCpf("101");

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jogadorDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Email invalido não cria Jogador")
    void invalidEmail() throws Exception {
        // given
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        jogadorDto.setEmail("abc");

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jogadorDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Busca uma jogador com Id válido")
    @Test
    void getOne() throws Exception {
        // given
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        Jogador jogador = jogadorMapper.toModel(jogadorDto);

        //when
        when(jogadorService.getOne(jogadorDto.getId())).thenReturn(Optional.of(jogador));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + jogadorDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(jogadorDto.getNome())))
                .andExpect(jsonPath("$.cpf", is(jogadorDto.getCpf())))
                .andExpect(jsonPath("$.email", is(jogadorDto.getEmail())));
    }

    @DisplayName("Busca um jogador com Id inválido")
    @Test
    void dontGetOneWithInvalidId() throws Exception {

        //when
        when(jogadorService.getOne(INVALID_ID)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @DisplayName("Busca todas os jogadors")
    @Test
    void getAll() throws Exception {
        // given
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        Jogador jogador = jogadorMapper.toModel(jogadorDto);
        PageImpl<Jogador> jogadorPage = new PageImpl<>(List.of(jogador));
        //when
        when(jogadorService.getAll(ArgumentMatchers.any())).thenReturn(jogadorPage);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is(jogadorDto.getNome())))
                .andExpect(jsonPath("$[0].cpf", is(jogadorDto.getCpf())))
                .andExpect(jsonPath("$[0].email", is(jogadorDto.getEmail())));
    }

    @Test
    @DisplayName("Atualiza Jogador")
    void update() throws Exception {
        // given
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        Jogador jogador = jogadorMapper.toModel(jogadorDto);

        // when
        when(jogadorService.getOne(VALID_ID)).thenReturn(Optional.of(jogador));
        when(jogadorService.save(jogador)).thenReturn(jogador);

        // then
        mockMvc.perform(put(API_URL_PATH+ "/" +VALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jogadorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(jogadorDto.getNome())));
    }

    @Test
    @DisplayName("Id inválido não atualiza Jogador")
    void dontUpdateWithInvalidID() throws Exception {
        // given
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        Jogador jogador = jogadorMapper.toModel(jogadorDto);

        // when
        when(jogadorService.getOne(INVALID_ID)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(put(API_URL_PATH+ "/" +INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jogadorDto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Deleta Jogador")
    void delete() throws Exception {
        // given
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        Jogador jogador = jogadorMapper.toModel(jogadorDto);

        // when
        when(jogadorService.getOne(VALID_ID)).thenReturn(Optional.of(jogador));
        doNothing().when(jogadorService).delete(jogador.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + VALID_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Não deleta jogador com Id inválido")
    void dontDeleteWithInvalidID() throws Exception {
        // given
        JogadorDto jogadorDto = JogadorBuilder.builder().build().jogadorDto();
        Jogador jogador = jogadorMapper.toModel(jogadorDto);

        // when
        when(jogadorService.getOne(INVALID_ID)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH+ "/" +INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(jogadorDto)))
                .andExpect(status().isBadRequest());

    }


}
