package com.sicredi.votacao.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SessaoDTO {
    private Long id;
    private Long pautaId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}