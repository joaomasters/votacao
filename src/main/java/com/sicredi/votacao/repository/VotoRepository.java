package com.sicredi.votacao.repository;

import com.sicredi.votacao.model.Sessao;
import com.sicredi.votacao.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsBySessaoIdAndAssociadoId(Sessao sessao, Long associadoId);
    List<Voto> findBySessaoIdIn(List<Long> sessaoIds);
    List<Voto> findBySessao(Sessao sessao);
    long countBySessaoAndVoto(Sessao sessao, boolean voto);

}
