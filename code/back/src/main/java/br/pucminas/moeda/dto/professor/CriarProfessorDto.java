package br.pucminas.moeda.dto.professor;

import br.pucminas.moeda.dto.usuario.CriarUsuarioDto;
import lombok.Data;

@Data
public class CriarProfessorDto extends CriarUsuarioDto {

    private String departamento;

}
