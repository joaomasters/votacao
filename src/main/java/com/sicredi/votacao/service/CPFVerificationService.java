package com.sicredi.votacao.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.swagger.models.HttpMethod;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Service
public class CPFVerificationService {
    private final RestTemplate restTemplate;

    @Autowired
    public CPFVerificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String verificarCPF(String cpf) {
        String url = "https://user-info.herokuapp.com/users/" + cpf;
        try {
            Map<String, String> response = restTemplate.getForObject(url, Map.class);
            return response.get("status");
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return "INVALID_CPF";
            }
            throw e;
        }
    }
    
    public String verificarCPF1(String cpf) {
        String url = "https://user-info.herokuapp.com/users/" + cpf;
        try {
            // Usando ParameterizedTypeReference para manter a segurança de tipos
            ResponseEntity<Map<String, Object>> response = restTemplate.postForEntity(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            // Verifica se o código de status é 200 OK antes de acessar os dados
            if (response.getStatusCode().is2xxSuccessful()) {
                Map<String, Object> responseBody = response.getBody();
                String status = (String) responseBody.get("status");
                return status;
            } else {
                return "INVALID_CPF";
            }
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return "INVALID_CPF";
            }
            throw e;
        }
    }
}
