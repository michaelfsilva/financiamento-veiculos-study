package com.financiamentos.veiculosstudy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldNameConstants
public class SimulacaoFinanciamento {
    private Cliente cliente;
    private float valorEntrada;
    private float valorFinanciamento;
    private int parcelas;
    private float valorParcela;
    private LocalDateTime dataSimulacao;
}
