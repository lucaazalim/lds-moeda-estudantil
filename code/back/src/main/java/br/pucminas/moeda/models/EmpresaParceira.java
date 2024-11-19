package br.pucminas.moeda.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class EmpresaParceira extends Usuario {

    @OneToMany(mappedBy = "empresaParceira")
    private Set<Vantagem> vantagens;

}
