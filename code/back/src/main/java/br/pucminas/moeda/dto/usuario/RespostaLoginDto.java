package br.pucminas.moeda.dto.usuario;

import lombok.Data;

@Data
public class RespostaLoginDto {

    private String token;
    private long expiraEm;

}
