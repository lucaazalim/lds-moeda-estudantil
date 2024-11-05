package br.pucminas.moeda.dto.vantagem;

import lombok.Data;

@Data
public class CriarVantagemDto {

    private String nome, descricao, foto;

    private int custo;

}
