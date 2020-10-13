package com.financiamentos.veiculosstudy.external.gateway;

import com.financiamentos.veiculosstudy.entity.Parcela;

import java.util.List;

public interface ParcelaGateway {
    /**
     * Get all parcelas
     *
     * @return list of parcelas
     */
    List<Parcela> getAllParcelas();
}
