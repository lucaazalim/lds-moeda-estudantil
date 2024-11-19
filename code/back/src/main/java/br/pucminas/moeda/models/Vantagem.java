package br.pucminas.moeda.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Vantagem {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    @NotBlank
    private String nome, descricao, foto;

    @Min(0)
    @Column(nullable = false)
    private int custo;

    @ManyToOne
    @JoinColumn(name = "empresa_parceira", nullable = false)
    @JsonIgnore
    private EmpresaParceira empresaParceira;

}
