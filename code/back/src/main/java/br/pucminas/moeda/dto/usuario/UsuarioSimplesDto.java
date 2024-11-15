package br.pucminas.moeda.dto.usuario;

import br.pucminas.moeda.enumeradores.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioSimplesDto {

    private Long id;
    private String nome;
    private TipoUsuario tipo;

}
