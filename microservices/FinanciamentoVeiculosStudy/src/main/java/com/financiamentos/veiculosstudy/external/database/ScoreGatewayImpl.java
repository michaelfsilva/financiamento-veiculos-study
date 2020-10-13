package com.financiamentos.veiculosstudy.external.database;

import com.financiamentos.veiculosstudy.entity.Score;
import com.financiamentos.veiculosstudy.external.database.repository.ScoreRepository;
import com.financiamentos.veiculosstudy.external.gateway.ScoreGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

import javax.transaction.TransactionalException;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.value;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScoreGatewayImpl implements ScoreGateway {
    private final ScoreRepository scoreRepository;

    @Override
    public Score findByPontuacao(final int pontuacao) {
        try {
            final Optional<Score> score = scoreRepository.findByPontuacao(pontuacao)
                    .map(scoreModel -> new ModelMapper().map(scoreModel, Score.class));

            return score.orElseThrow(ChangeSetPersister.NotFoundException::new);

        } catch (final Exception exception) {
            log.error("Error to find score by pontuacao on database: {}", value("pontuacao", pontuacao));
            throw new TransactionalException("Error to find score by pontuacao on database", exception);
        }
    }
}
