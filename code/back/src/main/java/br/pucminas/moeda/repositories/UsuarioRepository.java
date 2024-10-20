package br.pucminas.moeda.repositories;

import br.pucminas.moeda.models.Aluno;
import br.pucminas.moeda.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Esta classe é obrigatória para que seja possível usar o JPA e o Hibernate.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

}