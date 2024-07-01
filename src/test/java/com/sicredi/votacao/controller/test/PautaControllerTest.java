package com.sicredi.votacao.controller.test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.controller.PautaController;
import com.sicredi.votacao.model.Pauta;
import com.sicredi.votacao.service.PautaService;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(PautaController.class)
public class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PautaService pautaService;

    @Test
    public void testCreatePauta() throws Exception {
        Pauta pauta = new Pauta();
        pauta.setDescricao("Descrição da pauta");

        ArgumentCaptor<Pauta> captor = ArgumentCaptor.forClass(Pauta.class);
        when(pautaService.createPauta(captor.capture())).thenReturn(pauta);

       
        mockMvc.perform(post("/api/v1/pautas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(pauta)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.descricao", is("Descrição da pauta")));
    }

    @Test
    public void testGetAllPautas() throws Exception {
        List<Pauta> pautas = Arrays.asList(new Pauta(), new Pauta());

        when(pautaService.getAllPautas()).thenReturn(pautas);

        mockMvc.perform(get("/api/v1/pautas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetPautaById() throws Exception {
        Long pautaId = 1L;
        Pauta pauta = new Pauta();
        pauta.setId(pautaId);
        pauta.setDescricao("Descrição da pauta");

        when(pautaService.getPautaById(pautaId)).thenReturn(pauta);

        mockMvc.perform(get("/api/v1/pautas/{id}", pautaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.descricao", is("Descrição da pauta")));
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