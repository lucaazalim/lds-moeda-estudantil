package br.pucminas.moeda.dto.usuario;

import br.pucminas.moeda.dto.transacao.TransacaoDto;
import br.pucminas.moeda.enumeradores.TipoUsuario;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioDto {

    private Long id;
    private String nome;
    private String identificacao;
    private String email;
    private int saldo;
    private TipoUsuario tipo;

    private List<TransacaoDto> transacoesEnviadas;
    private List<TransacaoDto> transacoesRecebidas;

}
