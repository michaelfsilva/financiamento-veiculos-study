package com.financiamentos.veiculosstudy.integration;

import com.financiamentos.veiculosstudy.entity.Cliente;
import com.financiamentos.veiculosstudy.entity.SimulacaoFinanciamento;
import com.financiamentos.veiculosstudy.external.database.SimulacaoFinanciamentoGatewayImpl;
import com.financiamentos.veiculosstudy.external.database.model.ClienteModel;
import com.financiamentos.veiculosstudy.external.database.model.SimulacaoFinanciamentoModel;
import com.financiamentos.veiculosstudy.external.database.repository.ClienteRepository;
import com.financiamentos.veiculosstudy.external.database.repository.SimulacaoFinanciamentoRepository;
import com.financiamentos.veiculosstudy.external.gateway.ClienteGateway;
import com.financiamentos.veiculosstudy.external.gateway.SimulacaoFinanciamentoGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionalException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
class SimulacaoFinanciamentoGatewayTest {
    @Autowired
    SimulacaoFinanciamentoGateway simulacaoFinanciamentoGateway;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    SimulacaoFinanciamentoRepository simulacaoFinanciamentoRepository;

    SimulacaoFinanciamentoRepository simulacaoFinanciamentoRepositoryMock = mock(SimulacaoFinanciamentoRepository.class);
    ClienteGateway clienteGatewayMock = mock(ClienteGateway.class);
    SimulacaoFinanciamentoGateway simulacaoFinanciamentoGatewayMock = new SimulacaoFinanciamentoGatewayImpl(simulacaoFinanciamentoRepositoryMock, clienteGatewayMock);

    @Test
    void shouldSaveSimulacaoFinanciamento() {
        //Given a valid simulacaoFinanciamento
        final SimulacaoFinanciamento simulacaoFinanciamento = simulacaoFinanciamento();

        //And a Cliente already saved
        clienteRepository.save(ClienteModel.builder().cpf("123.456.789-00").build());

        //When call the gateway
        simulacaoFinanciamentoGateway.save(simulacaoFinanciamento);

        //Then values should be saved properly on DB
        final SimulacaoFinanciamentoModel simulacaoFinanciamentoModel = simulacaoFinanciamentoRepository.findByClienteCpf("123.456.789-00");
        assertEquals(simulacaoFinanciamentoModel.getValorEntrada(), simulacaoFinanciamento.getValorEntrada());
        assertEquals(simulacaoFinanciamentoModel.getValorFinanciamento(), simulacaoFinanciamento.getValorFinanciamento());
        assertEquals(simulacaoFinanciamentoModel.getDataSimulacao(), simulacaoFinanciamento.getDataSimulacao());
    }

    @Test
    void shouldSaveAllSimulacaoFinanciamento() {
        //Given a list of simulacaoFinanciamento and a clienteModel
        final List<SimulacaoFinanciamento> simulacaoFinanciamentoList = Arrays.asList(simulacaoFinanciamento(), simulacaoFinanciamento());
        final ClienteModel clienteModel = getClienteModel();

        //And a Cliente already saved
        clienteRepository.save(clienteModel);

        //When call the gateway
        simulacaoFinanciamentoGateway.saveAll(simulacaoFinanciamentoList);

        //Then values should be saved properly on DB
        final List<SimulacaoFinanciamentoModel> simulacaoFinanciamentoModelList = simulacaoFinanciamentoRepository.findAllByClienteCpf("123.456.789-00");
        assertEquals(simulacaoFinanciamentoModelList.get(0).getValorEntrada(), simulacaoFinanciamentoList.get(0).getValorEntrada());
        assertEquals(simulacaoFinanciamentoModelList.get(0).getValorFinanciamento(), simulacaoFinanciamentoList.get(0).getValorFinanciamento());
        assertEquals(simulacaoFinanciamentoModelList.get(0).getDataSimulacao(), simulacaoFinanciamentoList.get(0).getDataSimulacao());
        assertEquals(simulacaoFinanciamentoModelList.get(1).getValorEntrada(), simulacaoFinanciamentoList.get(1).getValorEntrada());
        assertEquals(simulacaoFinanciamentoModelList.get(1).getValorFinanciamento(), simulacaoFinanciamentoList.get(1).getValorFinanciamento());
        assertEquals(simulacaoFinanciamentoModelList.get(1).getDataSimulacao(), simulacaoFinanciamentoList.get(1).getDataSimulacao());
    }

    @Test
    void shouldGetExceptionSavingSimulacaoFinanciamento() {
        //Given a simulacaoFinanciamento
        final SimulacaoFinanciamento simulacaoFinanciamento = simulacaoFinanciamento();
        doThrow(TransactionalException.class).when(simulacaoFinanciamentoRepositoryMock).save(any(SimulacaoFinanciamentoModel.class));

        //When call the gateway should throw exception
        assertThrows(TransactionalException.class, () -> simulacaoFinanciamentoGatewayMock.save(simulacaoFinanciamento));
    }

    @Test
    void shouldGetExceptionSavingAllSimulacaoFinanciamento() {
        //Given a simulacaoFinanciamento
        final List<SimulacaoFinanciamento> simulacaoFinanciamentoList = Arrays.asList(simulacaoFinanciamento(), simulacaoFinanciamento());
        doThrow(TransactionalException.class).when(simulacaoFinanciamentoRepositoryMock).saveAll(any());

        //When call the gateway should throw exception
        assertThrows(TransactionalException.class, () -> simulacaoFinanciamentoGatewayMock.saveAll(simulacaoFinanciamentoList));
    }

    private SimulacaoFinanciamento simulacaoFinanciamento() {
        final Cliente cliente = Cliente.builder()
                .cpf("123.456.789-00")
                .build();

        return SimulacaoFinanciamento.builder()
                .cliente(cliente)
                .valorEntrada(10000.00f)
                .valorFinanciamento(35000.00f)
                .dataSimulacao(LocalDateTime.of(2020, Month.JULY, 20, 10, 20, 0))
                .build();
    }

    private ClienteModel getClienteModel() {
        return ClienteModel.builder()
                .id(123)
                .nome("José da Silva")
                .cpf("123.456.789-00")
                .dataNascimento(LocalDate.of(1990, Month.JULY, 20))
                .telefone("3211-6544")
                .renda(3000.00f)
                .possuiImovel(true)
                .build();
    }
}
