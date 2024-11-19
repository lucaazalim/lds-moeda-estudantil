package br.pucminas.moeda.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Aluno extends Usuario {

    @Column(unique = true)
    @NotBlank
    private String rg;

    @NotBlank
    private String endereco;

    @NotBlank
    private String curso;

}
