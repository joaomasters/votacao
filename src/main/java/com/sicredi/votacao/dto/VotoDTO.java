package com.sicredi.votacao.dto;

import lombok.Data;

@Data
public class VotoDTO {
    private Long id;
    private Long sessaoId;
    private Long associadoId;
    private Boolean voto;
}