package br.pucminas.moeda.dto.professor;

import br.pucminas.moeda.dto.usuario.UsuarioDto;
import lombok.Data;

@Data
public class ProfessorDto extends UsuarioDto {

    private String departamento;

}
