package com.sicredi.votacao.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Associado {
    @Id
    private Long id;
    private String cpf;
}