package com.sicredi.votacao.repository.test;

import com.sicredi.votacao.model.Pauta;
import com.sicredi.votacao.repository.PautaRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PautaRepositoryTest {

    @Autowired
    private PautaRepository pautaRepository;

    @Test
    void testCreatePauta() {
        Pauta pauta = new Pauta();
        pauta.setDescricao("Nova Pauta");
        pauta = pautaRepository.save(pauta);

        assertThat(pauta.getId()).isNotNull();
        assertThat(pauta.getDescricao()).isEqualTo("Nova Pauta");
    }
}
