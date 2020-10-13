package com.financiamentos.veiculosstudy.entity.endpoint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SimulacaoFinanciamentoRequest {
    private ClienteRequest clienteRequest;
    private SimulacaoRequest simulacaoRequest;
}

