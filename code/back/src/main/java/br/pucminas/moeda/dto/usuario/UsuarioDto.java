package br.pucminas.moeda.dto.usuario;

import br.pucminas.moeda.enumeradores.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioDto {

    private Long id;
    private String nome;
    private String identificacao;
    private String email;
    private int saldo;
    private TipoUsuario tipo;

}
