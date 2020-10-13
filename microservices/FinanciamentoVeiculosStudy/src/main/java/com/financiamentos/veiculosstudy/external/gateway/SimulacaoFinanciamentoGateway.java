package com.financiamentos.veiculosstudy.external.gateway;

import com.financiamentos.veiculosstudy.entity.SimulacaoFinanciamento;

import java.util.List;

public interface SimulacaoFinanciamentoGateway {
    /**
     * Save a simulacaoFinanciamento
     *
     * @param simulacaoFinanciamento
     */
    void save(SimulacaoFinanciamento simulacaoFinanciamento);

    /**
     * Save a list of simulacaoFinanciamento
     *
     * @param simulacaoFinanciamentoList
     */
    void saveAll(List<SimulacaoFinanciamento> simulacaoFinanciamentoList);
}
