package com.financiamentos.veiculosstudy.external.database;

import com.financiamentos.veiculosstudy.entity.SimulacaoFinanciamento;
import com.financiamentos.veiculosstudy.external.database.model.ClienteModel;
import com.financiamentos.veiculosstudy.external.database.model.SimulacaoFinanciamentoModel;
import com.financiamentos.veiculosstudy.external.database.repository.SimulacaoFinanciamentoRepository;
import com.financiamentos.veiculosstudy.external.gateway.ClienteGateway;
import com.financiamentos.veiculosstudy.external.gateway.SimulacaoFinanciamentoGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.transaction.TransactionalException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.value;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimulacaoFinanciamentoGatewayImpl implements SimulacaoFinanciamentoGateway {

    private final SimulacaoFinanciamentoRepository simulacaoFinanciamentoRepository;
    private final ClienteGateway clienteGateway;
    final ModelMapper modelMapper = new ModelMapper();

    @Override
    public void save(final SimulacaoFinanciamento simulacaoFinanciamento) {
        try {
            log.debug("SimulacaoFinanciamento will be persisted");
            final SimulacaoFinanciamentoModel simulacaoFinanciamentoModel = modelMapper.map(simulacaoFinanciamento, SimulacaoFinanciamentoModel.class);

            final Optional<ClienteModel> clienteModel = clienteGateway.findClienteModelByCpf(simulacaoFinanciamento.getCliente().getCpf());
            simulacaoFinanciamentoModel.getCliente().setId(clienteModel.map(ClienteModel::getId).orElse(0));

            simulacaoFinanciamentoRepository.save(simulacaoFinanciamentoModel);

        } catch (final Exception exception) {
            log.error("Error to persist data on database: {}", value("simulacaoFinanciamento", simulacaoFinanciamento));
            throw new TransactionalException("Error to persist data on database", exception);
        }
    }

    @Override
    public void saveAll(final List<SimulacaoFinanciamento> simulacaoFinanciamentoList) {
        try {
            log.debug("SimulacaoFinanciamentoList will be persisted");
            final List<SimulacaoFinanciamentoModel> simulacaoFinanciamentoModelList = new ArrayList<>();
            final Optional<ClienteModel> clienteModel = clienteGateway.findClienteModelByCpf(simulacaoFinanciamentoList.get(0).getCliente().getCpf());
            final int clienteId = clienteModel.map(ClienteModel::getId).orElse(0);

            simulacaoFinanciamentoList.forEach(simulacaoFinanciamento -> {
                final SimulacaoFinanciamentoModel simulacaoFinanciamentoModel = modelMapper.map(simulacaoFinanciamento, SimulacaoFinanciamentoModel.class);
                simulacaoFinanciamentoModel.getCliente().setId(clienteId);
                simulacaoFinanciamentoModelList.add(simulacaoFinanciamentoModel);
            });

            simulacaoFinanciamentoRepository.saveAll(simulacaoFinanciamentoModelList);

        } catch (final Exception exception) {
            log.error("Error to persist data on database: {}", value("simulacaoFinanciamentoList", simulacaoFinanciamentoList));
            throw new TransactionalException("Error to persist data on database", exception);
        }
    }
}
