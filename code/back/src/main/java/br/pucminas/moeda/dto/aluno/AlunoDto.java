package br.pucminas.moeda.dto.aluno;

import br.pucminas.moeda.dto.usuario.UsuarioDto;
import lombok.Data;

@Data
public class AlunoDto extends UsuarioDto {

    private String rg;
    private String endereco;
    private String curso;

}
