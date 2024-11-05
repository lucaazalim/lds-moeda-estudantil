package br.pucminas.moeda.dto.vantagem;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AtualizarVantagemDto {

    private String nome, descricao, foto;

    private int custo;

}
