package com.sicredi.votacao.service;

import com.sicredi.votacao.model.Associado;
import com.sicredi.votacao.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
public class AssociadoService {
    @Autowired
    private AssociadoRepository associadoRepository;

    private final RestTemplate restTemplate = new RestTemplate();

    public Associado createAssociado(Associado associado) {
        return associadoRepository.save(associado);
    }

    public boolean podeVotar(String cpf) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity("https://user-info.herokuapp.com/users/" + cpf, String.class);
            return response.getBody().contains("ABLE_TO_VOTE");
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false;
            }
            throw e;
        }
    }
}