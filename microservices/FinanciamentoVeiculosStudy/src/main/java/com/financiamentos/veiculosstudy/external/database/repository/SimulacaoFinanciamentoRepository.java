package com.financiamentos.veiculosstudy.external.database.repository;

import com.financiamentos.veiculosstudy.external.database.model.SimulacaoFinanciamentoModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@EnableTransactionManagement
public interface SimulacaoFinanciamentoRepository extends CrudRepository<SimulacaoFinanciamentoModel, Long> {
    SimulacaoFinanciamentoModel findByClienteCpf(String cpf);

    List<SimulacaoFinanciamentoModel> findAllByClienteCpf(String cpf);
}
