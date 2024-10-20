package br.pucminas.moeda.dto.aluno;

import br.pucminas.moeda.dto.usuario.CriarUsuarioDto;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
public class CriarAlunoDto extends CriarUsuarioDto {

    private String rg;
    private String endereco;
    private String curso;

}
