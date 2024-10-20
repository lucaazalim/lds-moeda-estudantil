package br.pucminas.moeda.dto.usuario;

import lombok.Data;
import lombok.Getter;

@Data
public abstract class CriarUsuarioDto {

    private String nome;
    private String identificacao;
    private String email;
    private String senha;

}
