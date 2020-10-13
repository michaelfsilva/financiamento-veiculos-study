package com.financiamentos.veiculosstudy.external.database.model;

import lombok.*;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@Table(name = "cliente")
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class ClienteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private float renda;

    @Column(columnDefinition = "BIT")
    private boolean possuiImovel;
}
