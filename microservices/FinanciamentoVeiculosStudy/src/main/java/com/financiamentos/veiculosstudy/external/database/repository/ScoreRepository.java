package com.financiamentos.veiculosstudy.external.database.repository;

import com.financiamentos.veiculosstudy.external.database.model.ScoreModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@EnableTransactionManagement
public interface ScoreRepository extends CrudRepository<ScoreModel, Long> {
    Optional<ScoreModel> findByPontuacao(int pontuacao);
}
