package com.sicredi.votacao.service;

import com.sicredi.votacao.exception.ResourceNotFoundException;
import com.sicredi.votacao.model.*;
import com.sicredi.votacao.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class VotoService {
    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private SessaoRepository sessaoRepository;

    public Voto createVoto(Long sessaoId, Long associadoId, Boolean voto) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
            .orElseThrow(() -> new ResourceNotFoundException("Sessao not found with id " + sessaoId));

        if (votoRepository.existsBySessaoIdAndAssociadoId(sessao, associadoId)) {
            throw new IllegalStateException("Associado já votou nesta pauta.");
        }

        Voto novoVoto = new Voto();
        novoVoto.setSessao(sessao);
        novoVoto.setAssociadoId(associadoId);
        novoVoto.setVoto(voto);

        return votoRepository.save(novoVoto);
    }

    public long countVotos(Long sessaoId, boolean voto) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
            .orElseThrow(() -> new ResourceNotFoundException("Sessao not found with id " + sessaoId));
        return votoRepository.countBySessaoAndVoto(sessao, voto);
    }
}