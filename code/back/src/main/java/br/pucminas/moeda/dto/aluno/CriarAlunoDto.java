package br.pucminas.moeda.dto.aluno;

import br.pucminas.moeda.dto.usuario.CriarUsuarioDto;
import lombok.Data;

@Data
public class CriarAlunoDto extends CriarUsuarioDto {

    private String rg;
    private String endereco;
    private String curso;

}
