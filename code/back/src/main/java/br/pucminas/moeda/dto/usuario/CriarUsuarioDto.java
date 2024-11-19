package br.pucminas.moeda.dto.usuario;

import lombok.Data;

@Data
public abstract class CriarUsuarioDto {

    private String nome;
    private String identificacao;
    private String email;
    private String senha;

}
