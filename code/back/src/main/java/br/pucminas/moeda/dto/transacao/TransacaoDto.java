package br.pucminas.moeda.dto.transacao;

import br.pucminas.moeda.dto.usuario.UsuarioSimplesDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransacaoDto {

    private long id;

    private int quantidade;

    private String motivo;

    private UsuarioSimplesDto de, para;

    private LocalDateTime criadaEm;

}
