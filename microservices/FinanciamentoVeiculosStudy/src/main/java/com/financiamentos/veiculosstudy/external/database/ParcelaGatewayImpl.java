package com.financiamentos.veiculosstudy.external.database;

import com.financiamentos.veiculosstudy.entity.Parcela;
import com.financiamentos.veiculosstudy.external.database.repository.ParcelaRepository;
import com.financiamentos.veiculosstudy.external.gateway.ParcelaGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.transaction.TransactionalException;
import java.util.List;
import java.util.stream.Collectors;

import static net.logstash.logback.argument.StructuredArguments.value;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParcelaGatewayImpl implements ParcelaGateway {
    private final ParcelaRepository parcelaRepository;

    @Override
    public List<Parcela> getAllParcelas() {
        try {
            return parcelaRepository.findAll().stream().map(parcelaModel -> new ModelMapper().map(parcelaModel, Parcela.class))
                    .collect(Collectors.toList());

        } catch (final Exception exception) {
            log.error("Error to find parcelas on database: {}", value("exception", exception.getMessage()));
            throw new TransactionalException("Error to find parcelas on database", exception);
        }
    }

}
