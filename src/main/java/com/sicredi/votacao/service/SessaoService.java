package com.sicredi.votacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicredi.votacao.exception.ResourceNotFoundException;
import com.sicredi.votacao.model.Pauta;
import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.repository.PautaRepository;
import com.sicredi.votacao.repository.SessaoRepository;

import java.time.LocalDateTime;

@Service
public class SessaoService {
    @Autowired
    private SessaoRepository sessaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    public Sessao createSessao(Long pautaId, LocalDateTime dataFim) {
        Pauta pauta = pautaRepository.findById(pautaId)
            .orElseThrow(() -> new ResourceNotFoundException("Pauta not found with id " + pautaId));
        
        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setDataInicio(LocalDateTime.now());
        sessao.setDataFim(dataFim != null ? dataFim : LocalDateTime.now().plusMinutes(1));
        
        return sessaoRepository.save(sessao);
    }
}