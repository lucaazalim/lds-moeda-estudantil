package br.pucminas.moeda.repositories;

import br.pucminas.moeda.models.Aluno;
import br.pucminas.moeda.models.Vantagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VantagemRepository extends JpaRepository<Vantagem, Long> {
}
