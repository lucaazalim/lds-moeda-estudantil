package br.pucminas.moeda.dto.aluno;

import br.pucminas.moeda.dto.usuario.AtualizarUsuarioDto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AtualizarAlunoDto extends AtualizarUsuarioDto {

    private String endereco;
    private String curso;

}
