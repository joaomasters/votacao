package com.sicredi.votacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sicredi.votacao.model.Pauta;
import com.sicredi.votacao.service.PautaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

@ApiOperation(value = "/api/v1/pautas")
@Api
@RestController
@RequestMapping("/api/v1/pautas")
public class PautaController {
    @Autowired
    private PautaService pautaService;

    @PostMapping
    public ResponseEntity<Pauta> createPauta(@RequestBody Pauta pauta) {
        Pauta createdPauta = pautaService.createPauta(pauta);
        return ResponseEntity.status(201).body(createdPauta);
    }

    @GetMapping
    public ResponseEntity<List<Pauta>> getAllPautas() {
        return ResponseEntity.ok(pautaService.getAllPautas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> getPautaById(@PathVariable Long id) {
        return ResponseEntity.ok(pautaService.getPautaById(id));
    }
}