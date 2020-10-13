package com.financiamentos.veiculosstudy.external.database;

import com.financiamentos.veiculosstudy.entity.Cliente;
import com.financiamentos.veiculosstudy.external.database.model.ClienteModel;
import com.financiamentos.veiculosstudy.external.database.repository.ClienteRepository;
import com.financiamentos.veiculosstudy.external.gateway.ClienteGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.transaction.TransactionalException;
import java.util.Optional;

import static net.logstash.logback.argument.StructuredArguments.value;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClienteGatewayImpl implements ClienteGateway {
    private final ClienteRepository clienteRepository;

    @Override
    public void save(final Cliente cliente) {
        try {
            log.debug("Cliente will be persisted");
            final ClienteModel clienteModel = new ModelMapper().map(cliente, ClienteModel.class);
            clienteRepository.save(clienteModel);

        } catch (final Exception exception) {
            log.error("Error to persist data on database: {}", value("cliente", cliente));
            throw new TransactionalException("Error to persist data on database", exception);
        }
    }

    @Override
    public Optional<ClienteModel> findClienteModelByCpf(final String cpf) {
        try {
            return clienteRepository.findClienteModelByCpf(cpf);
        } catch (final Exception exception) {
            log.error("Error to find cliente by cpf on database: {}", value("cpf", cpf));
            throw new TransactionalException("Error to find cliente by cpf on database", exception);
        }
    }
}
