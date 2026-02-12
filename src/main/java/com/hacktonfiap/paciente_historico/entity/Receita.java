package com.hacktonfiap.paciente_historico.entity;

import jakarta.persistence.*;
import jakarta.persistence.PrePersist;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "receitas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeOriginal;

    @Column(nullable = false)
    private String tipoArquivo;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] arquivo;

    private Long tamanho;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

}
