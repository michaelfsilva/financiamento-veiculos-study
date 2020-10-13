package com.financiamentos.veiculosstudy.integration;

import com.financiamentos.veiculosstudy.entity.Score;
import com.financiamentos.veiculosstudy.external.database.ScoreGatewayImpl;
import com.financiamentos.veiculosstudy.external.database.model.ScoreModel;
import com.financiamentos.veiculosstudy.external.database.repository.ScoreRepository;
import com.financiamentos.veiculosstudy.external.gateway.ScoreGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionalException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
class ScoreGatewayTest {
    @Autowired
    ScoreGateway scoreGateway;

    @Autowired
    ScoreRepository scoreRepository;

    ScoreRepository scoreRepositoryMock = mock(ScoreRepository.class);
    ScoreGateway scoreGatewayMock = new ScoreGatewayImpl(scoreRepositoryMock);

    @BeforeEach
    public void setup() {
        scoreRepository.save(ScoreModel.builder()
                .faixa("Baixa")
                .pontuacao(4)
                .juros(1.1f)
                .build());
    }

    @Test
    void shouldFindScoreByPontuacao() {
        //Given a valid score and repository mocked
        final int pontuacao = 4;

        //When call the gateway
        final Score score = scoreGateway.findByPontuacao(pontuacao);

        //Then should get correct values
        assertEquals("Baixa", score.getFaixa());
        assertEquals(1.1f, score.getJuros());
    }

    @Test
    void shouldGetExceptionIfScoreNotFound() {
        //Given a valid score
        final int pontuacao = 100;

        //When call the gateway exception should be thrown
        assertThrows(TransactionalException.class, () -> {
            scoreGatewayMock.findByPontuacao(pontuacao);
        });

        //Then should query for score on DB
        Mockito.verify(scoreRepositoryMock, Mockito.times(1)).findByPontuacao(pontuacao);
    }
}
