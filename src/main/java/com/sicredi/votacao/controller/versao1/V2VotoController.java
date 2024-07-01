package com.sicredi.votacao.controller.versao1;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.model.Voto;
import com.sicredi.votacao.service.SessaoService;
import com.sicredi.votacao.service.VotoService;

@RestController
@RequestMapping("/api/v2")
public class V2VotoController {

    @Autowired
    private SessaoService sessaoService;

    @Autowired
    private VotoService votoService;

    @PostMapping("/sessoes")
    public ResponseEntity<Sessao> createSessao(@RequestParam Long pautaId, @RequestParam(required = false) LocalDateTime duracao) {
        Sessao sessao = sessaoService.createSessao(pautaId, duracao);
        return ResponseEntity.status(201).body(sessao);
    }

    @PostMapping("/votos/sessoes/{sessaoId}")
    public ResponseEntity<Voto> createVoto(@PathVariable Long sessaoId, @RequestBody Voto voto) {
        Voto createdVoto = votoService.createVoto(sessaoId, voto.getAssociadoId(), voto.getVoto());
        return ResponseEntity.status(201).body(createdVoto);
    }

    @GetMapping("/votos/sessoes/{sessaoId}/contagem")
    public ResponseEntity<String> countVotos(@PathVariable Long sessaoId) {
        long countSim = votoService.countVotos(sessaoId, true);
        long countNao = votoService.countVotos(sessaoId, false);
        return ResponseEntity.ok("Sim: " + countSim + ", Não: " + countNao);
    }
}