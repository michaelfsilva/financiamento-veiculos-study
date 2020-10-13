package com.financiamentos.veiculosstudy.usecase;

import com.financiamentos.veiculosstudy.entity.*;
import com.financiamentos.veiculosstudy.entity.endpoint.ClienteRequest;
import com.financiamentos.veiculosstudy.entity.endpoint.SimulacaoFinanciamentoRequest;
import com.financiamentos.veiculosstudy.entity.endpoint.SimulacaoRequest;
import com.financiamentos.veiculosstudy.external.database.model.ClienteModel;
import com.financiamentos.veiculosstudy.external.gateway.ClienteGateway;
import com.financiamentos.veiculosstudy.external.gateway.ParcelaGateway;
import com.financiamentos.veiculosstudy.external.gateway.ScoreGateway;
import com.financiamentos.veiculosstudy.external.gateway.SimulacaoFinanciamentoGateway;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.transaction.TransactionalException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.value;

@Slf4j
@RequiredArgsConstructor
@AllArgsConstructor
@Transactional
@Service
public class HandleSimulation {

    @Autowired
    ClienteGateway clienteGateway;

    @Autowired
    ParcelaGateway parcelaGateway;

    @Autowired
    ScoreGateway scoreGateway;

    @Autowired
    SimulacaoFinanciamentoGateway simulacaoFinanciamentoGateway;

    final ModelMapper modelMapper = new ModelMapper();

    public ParcelaResponseList execute(final SimulacaoFinanciamentoRequest simulacaoFinanciamentoRequest) {
        final ClienteRequest clienteRequest = simulacaoFinanciamentoRequest.getClienteRequest();
        saveCliente(clienteRequest);

        final SimulacaoRequest simulacaoRequest = simulacaoFinanciamentoRequest.getSimulacaoRequest();

        final int pontuacao = getPontuacao(clienteRequest);

        final float valorFinanciamento = simulacaoRequest.getValorVeiculo() - simulacaoRequest.getValorEntrada();

        final Score juros = getScoreFromDatabase(pontuacao);
        final List<Parcela> opcoesDeParcelamento = getOpcoesDeParcelamento();

        final List<ParcelaResponse> parcelasCliente = new ArrayList<>();
        final LocalDateTime localDateTime = LocalDateTime.now();
        final List<SimulacaoFinanciamento> simulacaoFinanciamentoList = new ArrayList<>();

        //Calculo dos juros para cada quantidade de parcelas
        opcoesDeParcelamento.forEach(parcela -> {
            final double numerador = (parcela.getTaxaJuros() + juros.getJuros()) / 100; // transformados de porcentagem para numeração comum mesmo
            final double denominador = (1 - (Math.pow(1 / (1 + numerador), parcela.getQuantidade())));
            final float valorParcela = (float) (numerador / denominador) * valorFinanciamento;

            parcelasCliente.add(new ParcelaResponse(parcela.getQuantidade(), valorParcela));
            log.info("SimulacaoFinanciamento will be persisted, [parcelas: {}, valor{}]", parcela.getQuantidade(), valorParcela);

            simulacaoFinanciamentoList.add(SimulacaoFinanciamento.builder()
                    .cliente(modelMapper.map(clienteRequest, Cliente.class))
                    .dataSimulacao(localDateTime)
                    .valorEntrada(simulacaoRequest.getValorEntrada())
                    .valorFinanciamento(valorFinanciamento)
                    .parcelas(parcela.getQuantidade())
                    .valorParcela(valorParcela)
                    .build());
        });

        simulacaoFinanciamentoGateway.saveAll(simulacaoFinanciamentoList);

        return new ParcelaResponseList(parcelasCliente);
    }

    private int getIdade(final LocalDate dataDeNascimento) {
        return Period.between(dataDeNascimento, LocalDate.now()).getYears();
    }

    private int getPontuacao(final ClienteRequest clienteRequest) {
        final int idade = getIdade(clienteRequest.getDataNascimento());
        final float renda = clienteRequest.getRenda();

        int pontuacao;
        if (idade >= 18 && idade <= 29) {
            pontuacao = 2;
        } else if (idade >= 30 && idade <= 49) {
            pontuacao = 1;
        } else {
            pontuacao = 3;
        }

        pontuacao += clienteRequest.isPossuiImovel() ? 1 : 3;

        if (renda <= 3000.00) {
            pontuacao += 3;
        } else if (renda <= 5000.00) {
            pontuacao += 2;
        } else {
            pontuacao += 1;
        }

        return pontuacao;
    }

    private Score getScoreFromDatabase(final int pontuacao) {
        try {
            final Score score = scoreGateway.findByPontuacao(pontuacao);
            log.debug("Pontuacao found: {}", score.getPontuacao());
            return score;
        } catch (final Exception e) {
            log.error(e.getMessage());
            throw new TransactionalException(e.getMessage(), e);
        }
    }

    private void saveCliente(final ClienteRequest clienteRequest) {
        try {
            final Optional<ClienteModel> clienteModel = clienteGateway.findClienteModelByCpf(clienteRequest.getCpf());

            if (clienteModel.isPresent()) {
                if (!clienteRequest.getNome().equals(clienteModel.get().getNome())) {
                    throw new IllegalArgumentException("CPF já registrado com outro Nome");
                }
            } else {
                clienteGateway.save(modelMapper.map(clienteRequest, Cliente.class));
            }

        } catch (final IllegalArgumentException e) {
            log.error(e.getMessage() + ": {}", value("clienteRequest", clienteRequest));
            throw new IllegalArgumentException(e.getMessage(), e);

        } catch (final Exception e) {
            log.error(e.getMessage() + ": {}", value("clienteRequest", clienteRequest));
            throw new TransactionalException(e.getMessage(), e);
        }
    }

    private List<Parcela> getOpcoesDeParcelamento() {
        try {
            return parcelaGateway.getAllParcelas();
        } catch (final Exception e) {
            log.error(e.getMessage());
            throw new TransactionalException(e.getMessage(), e);
        }
    }
}
