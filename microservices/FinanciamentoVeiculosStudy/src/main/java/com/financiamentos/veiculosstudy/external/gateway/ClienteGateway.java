package com.financiamentos.veiculosstudy.external.gateway;

import com.financiamentos.veiculosstudy.entity.Cliente;
import com.financiamentos.veiculosstudy.external.database.model.ClienteModel;

import java.util.Optional;

public interface ClienteGateway {
    /**
     * Save data from cliente
     *
     * @param cliente
     */
    void save(Cliente cliente);

    /**
     * Search in cliente by CPF
     *
     * @param cpf
     * @return
     */
    Optional<ClienteModel> findClienteModelByCpf(String cpf);
}
