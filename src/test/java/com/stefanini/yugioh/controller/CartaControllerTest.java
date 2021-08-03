package com.stefanini.yugioh.controller;

import com.stefanini.yugioh.builder.CartaBuilder;
import com.stefanini.yugioh.dto.CartaDto;
import com.stefanini.yugioh.mapper.CartaMapper;
import com.stefanini.yugioh.model.Carta;
import com.stefanini.yugioh.service.CartaService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.List;
import java.util.Optional;

import static com.stefanini.yugioh.utils.JsonConvertionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CartaControllerTest {

    private static final String API_URL_PATH = "/api/v1/cards";
    private static final long VALID_ID= 1L;
    private static final long INVALID_ID= 2L;

    private MockMvc mockMvc;

    @Mock
    private CartaService cartaService;

    private CartaMapper cartaMapper = CartaMapper.getInstance();

    @InjectMocks
    private CartaController cartaController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartaController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    @DisplayName("Salva nova Carta")
    void save() throws Exception {
        // given

        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        Carta carta = cartaMapper.toModel(cartaDto);

        // when
        when(cartaService.save(carta)).thenReturn(carta);

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cartaDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome", is(cartaDto.getNome())));
    }

    @Test
    @DisplayName("Nome muito pequeno para nova Carta")
    void tooSmallName() throws Exception {
        // given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        cartaDto.setNome("ab");

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cartaDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Nome muito grande para nova Carta")
    void tooBigName() throws Exception {
        // given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        cartaDto.setNome("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cartaDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Atributo maior que deveria para nova Carta")
    void tooBigAttribute() throws Exception {
        // given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        cartaDto.setAtributoAtaque(101);

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cartaDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Atributo menor que deveria para nova Carta")
    void toSmallAttibute() throws Exception {
        // given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        cartaDto.setAtributoAtaque(-1);

        // then
        mockMvc.perform(post(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cartaDto)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("Busca uma carta com Id válido")
    @Test
    void getOne() throws Exception {
        // given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        Carta carta = cartaMapper.toModel(cartaDto);

        //when
        when(cartaService.getOne(cartaDto.getId())).thenReturn(Optional.of(carta));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + cartaDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(cartaDto.getNome())))
                .andExpect(jsonPath("$.detalhes", is(cartaDto.getDetalhes())))
                .andExpect(jsonPath("$.atributoDefesa", is(cartaDto.getAtributoDefesa())));
    }

    @DisplayName("Busca uma carta com Id inválido")
    @Test
    void dontGetOneWithInvalidId() throws Exception {

        //when
        when(cartaService.getOne(INVALID_ID)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH + "/" + INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @DisplayName("Busca todas as cartas")
    @Test
    void getAll() throws Exception {
        // given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        Carta carta = cartaMapper.toModel(cartaDto);

        //when
        when(cartaService.getAll()).thenReturn(List.of(carta));

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome", is(cartaDto.getNome())))
                .andExpect(jsonPath("$[0].detalhes", is(cartaDto.getDetalhes())))
                .andExpect(jsonPath("$[0].atributoDefesa", is(cartaDto.getAtributoDefesa())));
    }

    @Test
    @DisplayName("Atualiza Carta")
    void update() throws Exception {
        // given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        Carta carta = cartaMapper.toModel(cartaDto);

        // when
        when(cartaService.getOne(VALID_ID)).thenReturn(Optional.of(carta));
        when(cartaService.save(carta)).thenReturn(carta);

        // then
        mockMvc.perform(put(API_URL_PATH+ "/" +VALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cartaDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome", is(cartaDto.getNome())));
    }

    @Test
    @DisplayName("Id inválido não atualiza Carta")
    void dontUpdateWithInvalidID() throws Exception {
        // given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        Carta carta = cartaMapper.toModel(cartaDto);

        // when
        when(cartaService.getOne(INVALID_ID)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(put(API_URL_PATH+ "/" +INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cartaDto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @DisplayName("Deleta Carta")
    void delete() throws Exception {
        // given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        Carta carta = cartaMapper.toModel(cartaDto);

        // when
        when(cartaService.getOne(VALID_ID)).thenReturn(Optional.of(carta));
        doNothing().when(cartaService).delete(carta.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH + "/" + VALID_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Não deleta carta com Id inválido")
    void dontDeleteWithInvalidID() throws Exception {
        // given
        CartaDto cartaDto = CartaBuilder.builder().build().cartaDto();
        Carta carta = cartaMapper.toModel(cartaDto);

        // when
        when(cartaService.getOne(INVALID_ID)).thenReturn(Optional.empty());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(API_URL_PATH+ "/" +INVALID_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(cartaDto)))
                .andExpect(status().isBadRequest());

    }


}
