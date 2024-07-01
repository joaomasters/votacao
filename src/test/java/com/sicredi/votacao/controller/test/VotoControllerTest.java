package com.sicredi.votacao.controller.test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.controller.VotoController;
import com.sicredi.votacao.model.Voto;
import com.sicredi.votacao.service.VotoService;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.Matchers.*;



@RunWith(SpringRunner.class)
@WebMvcTest(VotoController.class)
public class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VotoService votoService;

    @Test
    public void testCreateVoto() throws Exception {
        Long sessaoId = 1L;
        Voto voto = new Voto();
        voto.setAssociadoId(1L);
        voto.setVoto(true);

        when(votoService.createVoto(anyLong(), anyLong(), anyBoolean())).thenReturn(voto);

        mockMvc.perform(post("/api/v1/votos/sessoes/{sessaoId}", sessaoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(voto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.associadoId", is(1)))
                .andExpect(jsonPath("$.voto", is(true)));
    }

    @Test
    public void testGetVotosBySessaoId() throws Exception {
        Long sessaoId = 1L;
        List<Voto> votos = Arrays.asList(new Voto(), new Voto());

        when(votoService.getVotosBySessaoId(sessaoId)).thenReturn(votos);

        mockMvc.perform(get("/api/v1/votos/sessoes/{sessaoId}", sessaoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testCountVotos() throws Exception {
        Long sessaoId = 1L;

        when(votoService.countVotos(sessaoId, true)).thenReturn(3L);
        when(votoService.countVotos(sessaoId, false)).thenReturn(2L);

        mockMvc.perform(get("/api/v1/votos/sessoes/{sessaoId}/contagem", sessaoId))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sim: 3, Não: 2")));
    }

    // Método auxiliar para converter objetos para JSON
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
