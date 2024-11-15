package br.pucminas.moeda.dto.transacao;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CriarTransacaoDto {

    private int quantidade;

    private String motivo;

    private long paraId;

}
