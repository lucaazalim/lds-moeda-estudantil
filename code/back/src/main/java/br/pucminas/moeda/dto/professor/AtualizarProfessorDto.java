package br.pucminas.moeda.dto.professor;

import br.pucminas.moeda.dto.usuario.AtualizarUsuarioDto;
import lombok.Data;

@Data
public class AtualizarProfessorDto extends AtualizarUsuarioDto {

    private String departamento;

}
