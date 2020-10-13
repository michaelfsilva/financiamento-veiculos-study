package com.financiamentos.veiculosstudy.integration;

import com.financiamentos.veiculosstudy.entity.Cliente;
import com.financiamentos.veiculosstudy.external.database.ClienteGatewayImpl;
import com.financiamentos.veiculosstudy.external.database.repository.ClienteRepository;
import com.financiamentos.veiculosstudy.external.gateway.ClienteGateway;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.transaction.TransactionalException;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteGatewayTest {
    ClienteRepository clienteRepository = mock(ClienteRepository.class);
    ClienteGateway clienteGateway = new ClienteGatewayImpl(clienteRepository);

    @Test
    void shouldSaveCliente() {
        //Given a cliente
        final Cliente cliente = getCliente();

        //When call the gateway
        clienteGateway.save(cliente);

        //Then should save cliente on DB
        verify(clienteRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void shouldGetExceptionSavingCliente() {
        //Given a cliente
        final Cliente cliente = getCliente();
        doThrow(TransactionalException.class).when(clienteRepository).save(any());

        //When call the gateway should throw exception
        assertThrows(Exception.class, () -> clienteGateway.save(cliente));
    }

    @Test
    void shouldFindClienteModelByCpf() {
        //Given a cpf
        final String cpf = "123.456.789-00";

        //When call the gateway
        clienteGateway.findClienteModelByCpf(cpf);

        //Then should find cliente on DB
        verify(clienteRepository, times(1)).findClienteModelByCpf(any());
    }

    @Test
    void shouldGetExceptionFindingClienteModelByCpf() {
        //Given a cpf
        final String cpf = "123.456.789-00";
        doThrow(TransactionalException.class).when(clienteRepository).findClienteModelByCpf(any());

        //When call the gateway should throw exception
        assertThrows(Exception.class, () -> clienteGateway.findClienteModelByCpf(cpf));
    }

    private Cliente getCliente() {
        return Cliente.builder()
                .nome("Teste Gateway")
                .cpf("123.456.789-00")
                .dataNascimento(LocalDate.of(1980, Month.AUGUST, 28))
                .telefone("3211-6544")
                .renda(2500.00f)
                .possuiImovel(true)
                .build();
    }
}
