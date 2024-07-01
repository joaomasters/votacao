package com.sicredi.votacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.service.SessaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.time.LocalDateTime;

@Api(value = "Sessao Controller")
@RestController
@RequestMapping("/api/v1/sessoes")
public class SessaoController {
	
	@Autowired
    private SessaoService sessaoService;

    @PostMapping("/pautas/{pautaId}")
    public ResponseEntity<Sessao> createSessao(@PathVariable Long pautaId, 
                                               @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        Sessao createdSessao = sessaoService.createSessao(pautaId, dataFim);
        return ResponseEntity.status(201).body(createdSessao);
    }
}