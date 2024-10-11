package br.pucminas.moeda.repositories;

import br.pucminas.moeda.models.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Esta classe é obrigatória para que seja possível usar o JPA e o Hibernate.
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    Optional<Aluno> findByEmail(String cpf);

}