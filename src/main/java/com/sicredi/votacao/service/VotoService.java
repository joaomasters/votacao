package com.sicredi.votacao.service;

import com.sicredi.votacao.exception.ResourceNotFoundException;
import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.model.Voto;
import com.sicredi.votacao.repository.SessaoRepository;
import com.sicredi.votacao.repository.VotoRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Voto> getVotosBySessaoId(Long sessaoId) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Sessao not found with id " + sessaoId));
        return votoRepository.findBySessao(sessao);
    }

    public long countVotos(Long sessaoId, boolean voto) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Sessao not found with id " + sessaoId));
        return votoRepository.countBySessaoAndVoto(sessao, voto);
    }
}