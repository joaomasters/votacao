package com.sicredi.votacao.service;

import com.sicredi.votacao.exception.ResourceNotFoundException;
import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.model.Voto;
import com.sicredi.votacao.repository.SessaoRepository;
import com.sicredi.votacao.repository.VotoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private SessaoRepository sessaoRepository;
    
    @Autowired
    private AssociadoService associadoService;

    public Voto createVoto(Long sessaoId, Long associadoId, Boolean voto) {
        // Busca a sessão pelo ID
        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Sessao not found with id " + sessaoId));
        
     // Verifica se o associado pode votar
        if (!associadoService.podeVotar(associadoId)) {
            throw new IllegalStateException("Associado não pode votar.");
        }
 
        // Verifica se o associado já votou nesta sessão pelo ID do associado e ID da sessão
        if (votoRepository.existsBySessaoIdAndAssociadoId(sessao.getId(), associadoId)) {
            throw new IllegalStateException("Associado já votou nesta pauta.");
        }

        // Cria um novo voto
        Voto novoVoto = new Voto();
        novoVoto.setSessao(sessao);
        novoVoto.setAssociadoId(associadoId); // Associado é associado ao voto
        novoVoto.setVoto(voto);

        // Salva o voto no banco de dados
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
