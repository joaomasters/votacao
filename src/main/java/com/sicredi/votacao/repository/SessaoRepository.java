package com.sicredi.votacao.repository;

import com.sicredi.votacao.model.Sessao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SessaoRepository extends JpaRepository<Sessao, Long> {
    List<Sessao> findByPautaId(Long pautaId);
}
