package com.financiamentos.veiculosstudy.external.gateway;

import com.financiamentos.veiculosstudy.entity.Score;

public interface ScoreGateway {
    /**
     * Find score by pontuacao
     *
     * @param pontuacao
     * @return score;
     */
    Score findByPontuacao(int pontuacao);
}
