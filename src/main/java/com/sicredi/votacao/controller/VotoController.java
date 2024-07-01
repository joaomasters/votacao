package com.sicredi.votacao.controller;

import com.sicredi.votacao.model.Voto;
import com.sicredi.votacao.service.VotoService;
import io.swagger.annotations.Api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Voto Controller")
@RestController
@RequestMapping("/api/v1/votos")
public class VotoController {

    @Autowired
    private VotoService votoService;

    @PostMapping("/sessoes/{sessaoId}")
    public ResponseEntity<Voto> createVoto(@PathVariable Long sessaoId, @RequestBody Voto voto) {
        Voto createdVoto = votoService.createVoto(sessaoId, voto.getAssociadoId(), voto.getVoto());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVoto);
    }

    @GetMapping("/sessoes/{sessaoId}")
    public ResponseEntity<List<Voto>> getVotosBySessaoId(@PathVariable Long sessaoId) {
        List<Voto> votos = votoService.getVotosBySessaoId(sessaoId);
        return ResponseEntity.ok(votos);
    }

    @GetMapping("/sessoes/{sessaoId}/contagem")
    public ResponseEntity<String> countVotos(@PathVariable Long sessaoId) {
        long votosSim = votoService.countVotos(sessaoId, true);
        long votosNao = votoService.countVotos(sessaoId, false);
        return ResponseEntity.ok("Sim: " + votosSim + ", Não: " + votosNao);
    }
}

