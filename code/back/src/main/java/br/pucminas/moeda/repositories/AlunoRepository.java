package br.pucminas.moeda.repositories;

import br.pucminas.moeda.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
