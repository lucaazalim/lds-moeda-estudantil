package br.pucminas.moeda.models;

import br.pucminas.moeda.enumeradores.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Aluno extends Usuario {

    private TipoUsuario tipo = TipoUsuario.ALUNO;

    @Column(unique = true)
    @NotBlank
    private String rg;

    @NotBlank
    private String endereco;

    @NotBlank
    private String curso;

}
