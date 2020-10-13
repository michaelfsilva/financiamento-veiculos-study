package com.financiamentos.veiculosstudy.external.database.repository;

import com.financiamentos.veiculosstudy.external.database.model.ClienteModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@EnableTransactionManagement
public interface ClienteRepository extends CrudRepository<ClienteModel, Long> {
    Optional<ClienteModel> findClienteModelByCpf(String cpf);
}
