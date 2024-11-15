package br.pucminas.moeda.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Min(1)
    @Column(nullable = false)
    private int quantidade;

    @NotBlank
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "de_id", nullable = false)
    @JsonIgnore
    private Usuario de;

    @ManyToOne
    @JoinColumn(name = "para_id", nullable = false)
    @JsonIgnore
    private Usuario para;

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadaEm;

    @PrePersist
    protected void onCreate() {
        criadaEm = LocalDateTime.now();
    }

}
