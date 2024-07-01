package com.sicredi.votacao.controller.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.sicredi.votacao.controller.VotoController;
import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.model.Voto;
import com.sicredi.votacao.service.VotoService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@WebMvcTest(VotoController.class)
public class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotoService votoService;

    @Test
    void createVoto_shouldReturnCreatedVoto() throws Exception {
        Long sessaoId = 1L;
        Sessao sessao = new Sessao();
        sessao.setId(sessaoId);

        Voto voto = new Voto();
        voto.setId(1L);
        voto.setSessao(sessao);
        voto.setAssociadoId(1L);
        voto.setVoto(true);

        when(votoService.createVoto(anyLong(), anyLong(), any(Boolean.class))).thenReturn(voto);

        mockMvc.perform(post("/api/v1/votos/sessoes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"associadoId\": 1, \"voto\": true }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1))) // Verifica o ID do voto criado
                .andExpect(jsonPath("$.sessao.id", is(1))) // Verifica o ID da sessao no voto criado
                .andExpect(jsonPath("$.associadoId", is(1))) // Verifica o associadoId no voto criado
                .andExpect(jsonPath("$.voto", is(true))); // Verifica o voto no voto criado
    }

    @Test
    void countVotos_shouldReturnVoteCount() throws Exception {
        when(votoService.countVotos(anyLong(), any(Boolean.class))).thenReturn(5L);

        mockMvc.perform(get("/api/v1/votos/sessoes/1/contagem"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Sim", is(5))) // Verifica o número de votos 'Sim'
                .andExpect(jsonPath("$.Não", is(0))); // Verifica o número de votos 'Não'
    }

}
