package br.pucminas.moeda.repositories;

import br.pucminas.moeda.models.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
}
