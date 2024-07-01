package com.sicredi.votacao.integration.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sicredi.votacao.model.Voto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class VotacaoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllVotos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/votos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
    }

    @Test
    public void testCreateVoto() throws Exception {
        Voto newVoto = new Voto(); // Exemplo de criação de um novo voto
        String jsonBody = objectMapper.writeValueAsString(newVoto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testGetVotoById() throws Exception {
        // Supondo que você tenha um ID válido de um voto existente
        String votoId = "1";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/votos/{id}", votoId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
  
    }

}
