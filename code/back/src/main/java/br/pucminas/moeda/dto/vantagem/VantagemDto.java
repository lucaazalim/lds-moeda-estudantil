package br.pucminas.moeda.dto.vantagem;

import lombok.Data;

@Data
public class VantagemDto {

    private String nome, descricao, foto;

    private int custo;

    private long empresaParceiraId;

}
