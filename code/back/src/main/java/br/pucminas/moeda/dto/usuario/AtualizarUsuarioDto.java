package br.pucminas.moeda.dto.usuario;

import lombok.Data;

@Data
public class AtualizarUsuarioDto {

    private String nome;
    private String email;
    private String senha;

}
