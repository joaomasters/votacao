package com.sicredi.votacao.controller;

import com.sicredi.votacao.model.*;
import com.sicredi.votacao.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/votos")
public class VotoController  {

	 @Autowired
	    private VotoService votoService;

	    @PostMapping("/sessoes/{sessaoId}")
	    public ResponseEntity<Voto> createVoto(@PathVariable Long sessaoId, @RequestBody Voto voto) {
	        Voto createdVoto = votoService.createVoto(sessaoId, voto.getAssociadoId(), voto.getVoto());
	        return ResponseEntity.status(201).body(createdVoto);
	    }

	    @GetMapping("/sessoes/{sessaoId}/contagem")
	    public ResponseEntity<String> countVotos(@PathVariable Long sessaoId) {
	        long votosSim = votoService.countVotos(sessaoId, true);
	        long votosNao = votoService.countVotos(sessaoId, false);
	        return ResponseEntity.ok("Sim: " + votosSim + ", Não: " + votosNao);
	    }
}
