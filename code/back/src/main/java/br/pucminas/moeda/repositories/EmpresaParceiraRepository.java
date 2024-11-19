package br.pucminas.moeda.repositories;

import br.pucminas.moeda.models.EmpresaParceira;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaParceiraRepository extends JpaRepository<EmpresaParceira, Long> {
}
