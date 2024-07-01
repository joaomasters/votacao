package com.sicredi.votacao.service;

import com.sicredi.votacao.model.Associado;
import com.sicredi.votacao.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AssociadoService {

    @Autowired
    private AssociadoRepository associadoRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String USER_INFO_API_URL = "https://user-info.herokuapp.com/users/";
    private static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";
    private static final String UNABLE_TO_VOTE = "UNABLE_TO_VOTE";


    public Associado createAssociado(Associado associado) {
        return associadoRepository.save(associado);
    }

    public boolean podeVotar(Long associadoId) {
        Associado associado = associadoRepository.findById(associadoId)
                .orElseThrow(() -> new IllegalArgumentException("Associado não encontrado com ID: " + associadoId));

        String cpf = associado.getCpf();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(USER_INFO_API_URL + cpf, String.class);
            if (response.getBody().contains(ABLE_TO_VOTE)) {
                return true;
            } else if (response.getBody().contains(UNABLE_TO_VOTE)) {
                return false;
            } else {
                throw new IllegalStateException("Resposta inesperada do serviço externo");
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return false; // CPF inválido
            }
            throw e;
        }
    }
}
