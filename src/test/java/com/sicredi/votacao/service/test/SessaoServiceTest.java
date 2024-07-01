package com.sicredi.votacao.service.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sicredi.votacao.exception.ResourceNotFoundException;
import com.sicredi.votacao.model.Pauta;
import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.repository.PautaRepository;
import com.sicredi.votacao.repository.SessaoRepository;
import com.sicredi.votacao.service.SessaoService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private SessaoService sessaoService;

    @Test
    void createSessao_shouldCreateSessao() {
        Long pautaId = 1L;
        Pauta pauta = new Pauta();
        pauta.setId(pautaId);

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));

        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setDataInicio(LocalDateTime.now());
        sessao.setDataFim(LocalDateTime.now().plusMinutes(1));

        when(sessaoRepository.save(any(Sessao.class))).thenReturn(sessao);

        Sessao createdSessao = sessaoService.createSessao(pautaId, null);

        assertNotNull(createdSessao);
        assertEquals(pauta, createdSessao.getPauta());
    }

    @Test
    void createSessao_shouldThrowResourceNotFoundException() {
        Long pautaId = 1L;
        when(pautaRepository.findById(pautaId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sessaoService.createSessao(pautaId, null));
    }
}