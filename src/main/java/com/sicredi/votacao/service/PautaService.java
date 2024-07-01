package com.sicredi.votacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sicredi.votacao.exception.ResourceNotFoundException;
import com.sicredi.votacao.model.Pauta;
import com.sicredi.votacao.repository.PautaRepository;

import java.util.List;

@Service
public class PautaService {
    @Autowired
    private PautaRepository pautaRepository;

    public Pauta createPauta(Pauta pauta) {
        return pautaRepository.save(pauta);
    }

    public List<Pauta> getAllPautas() {
        return pautaRepository.findAll();
    }

    public Pauta getPautaById(Long id) {
        return pautaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pauta not found with id " + id));
    }
}