package com.financiamentos.veiculosstudy.external.database.repository;

import com.financiamentos.veiculosstudy.external.database.model.ParcelaModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@EnableTransactionManagement
public interface ParcelaRepository extends CrudRepository<ParcelaModel, Long> {
    List<ParcelaModel> findAll();
}
