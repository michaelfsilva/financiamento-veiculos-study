package com.financiamentos.veiculosstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldNameConstants
public class Cliente {
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String telefone;
    private float renda;
    private boolean possuiImovel;
}
