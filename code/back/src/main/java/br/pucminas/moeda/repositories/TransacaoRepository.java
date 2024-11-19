package br.pucminas.moeda.repositories;

import br.pucminas.moeda.models.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
