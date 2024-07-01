package com.sicredi.votacao.service.test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.sicredi.votacao.exception.ResourceNotFoundException;
import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.model.Voto;
import com.sicredi.votacao.repository.SessaoRepository;
import com.sicredi.votacao.repository.VotoRepository;
import com.sicredi.votacao.service.VotoService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private SessaoRepository sessaoRepository;

    @InjectMocks
    private VotoService votoService;

    @Test
    void createVoto_shouldCreateVoto() {
        Long sessaoId = 1L;
        Long associadoId = 1L;
        boolean voto = true;

        Sessao sessao = new Sessao();
        sessao.setId(sessaoId);

        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessao));
        when(votoRepository.existsBySessaoIdAndAssociadoId(sessao.getId(), associadoId)).thenReturn(false);

        Voto novoVoto = new Voto();
        novoVoto.setSessao(sessao);
        novoVoto.setAssociadoId(associadoId);
        novoVoto.setVoto(voto);

        when(votoRepository.save(any(Voto.class))).thenReturn(novoVoto);

        Voto createdVoto = votoService.createVoto(sessaoId, associadoId, voto);

        assertNotNull(createdVoto);
        assertEquals(sessao, createdVoto.getSessao());
        assertEquals(associadoId, createdVoto.getAssociadoId());
        assertTrue(createdVoto.getVoto());
    }

    @Test
    void createVoto_shouldThrowResourceNotFoundException() {
        Long sessaoId = 1L;
        Long associadoId = 1L;
        boolean voto = true;

        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> votoService.createVoto(sessaoId, associadoId, voto));
    }

    @Test
    void countVotos_shouldReturnVotoCount() {
        Long sessaoId = 1L;
        boolean voto = true;

        Sessao sessao = new Sessao();
        sessao.setId(sessaoId);

        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(sessao));
        when(votoRepository.countBySessaoAndVoto(sessao, voto)).thenReturn(5L);

        long count = votoService.countVotos(sessaoId, voto);

        assertEquals(5L, count);
    }
}
