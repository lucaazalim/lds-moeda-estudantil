package br.pucminas.moeda.dto.transacao;

import lombok.Data;

@Data
public class CriarTransacaoDto {

    private int quantidade;

    private String motivo;

    private long paraId;

}
