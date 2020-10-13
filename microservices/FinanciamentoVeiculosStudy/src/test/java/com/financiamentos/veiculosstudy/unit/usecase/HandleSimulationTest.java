package com.financiamentos.veiculosstudy.unit.usecase;

import com.financiamentos.veiculosstudy.entity.Cliente;
import com.financiamentos.veiculosstudy.entity.Parcela;
import com.financiamentos.veiculosstudy.entity.ParcelaResponseList;
import com.financiamentos.veiculosstudy.entity.Score;
import com.financiamentos.veiculosstudy.entity.endpoint.ClienteRequest;
import com.financiamentos.veiculosstudy.entity.endpoint.SimulacaoFinanciamentoRequest;
import com.financiamentos.veiculosstudy.entity.endpoint.SimulacaoRequest;
import com.financiamentos.veiculosstudy.external.database.model.ClienteModel;
import com.financiamentos.veiculosstudy.external.gateway.ClienteGateway;
import com.financiamentos.veiculosstudy.external.gateway.ParcelaGateway;
import com.financiamentos.veiculosstudy.external.gateway.ScoreGateway;
import com.financiamentos.veiculosstudy.external.gateway.SimulacaoFinanciamentoGateway;
import com.financiamentos.veiculosstudy.usecase.HandleSimulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import javax.transaction.TransactionalException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HandleSimulationTest {
    private static final String NOME = "Teste";
    private static final String CPF = "123.456.789-00";
    private static final LocalDate DATA_NASCIMENTO_BAIXO_RISCO = LocalDate.of(1980, Month.AUGUST, 2);
    private static final LocalDate DATA_NASCIMENTO_MEDIO_RISCO = LocalDate.of(1998, Month.JANUARY, 2);
    private static final LocalDate DATA_NASCIMENTO_ALTO_RISCO_MENOR_IDADE = LocalDate.of(2015, Month.AUGUST, 2);
    private static final LocalDate DATA_NASCIMENTO_ALTO_RISCO_MAIOR_IDADE = LocalDate.of(1950, Month.AUGUST, 2);
    private static final String TELEFONE = "3211-6544";
    private static final float VALOR_ENTRADA = 5000f;
    private static final float VALOR_VEICULO = 30000f;

    private static final float VINTE_QUATRO_BAIXO_RISCO = 1233.6555f;
    private static final float TRINTA_SEIS_BAIXO_RISCO = 934.19275f;
    private static final float VINTE_QUATRO_MEDIO_RISCO = 1902.6978f;
    private static final float TRINTA_SEIS_MEDIO_RISCO = 1671.3263f;
    private static final float VINTE_QUATRO_ALTO_RISCO = 2707.3652f;
    private static final float TRINTA_SEIS_ALTO_RISCO = 2569.7793f;
    private static final String CPF_JA_REGISTRADO_ERRO = "CPF já registrado com outro Nome";
    private static final String SCORE_FAIXA_BAIXO = "Baixo";
    private static final String SCORE_FAIXA_ALTO = "Alto";
    private static final String SCORE_FAIXA_MEDIO = "Medio";
    private static final float RENDA_ALTO_RISCO = 2000f;
    private static final float RENDA_MEDIO_RISCO = 3500f;
    private static final float RENDA_BAIXO_RISCO = 6000f;

    private ClienteRequest clienteRequest;
    private SimulacaoRequest simulacaoRequest;
    private Score scoreMock;

    private final ClienteGateway clienteGateway = mock(ClienteGateway.class);
    private final ParcelaGateway parcelaGateway = mock(ParcelaGateway.class);
    private final ScoreGateway scoreGateway = mock(ScoreGateway.class);
    private final SimulacaoFinanciamentoGateway simulacaoFinanciamentoGateway = mock(SimulacaoFinanciamentoGateway.class);

    final ModelMapper modelMapper = new ModelMapper();

    private final HandleSimulation handleSimulation = new HandleSimulation(clienteGateway, parcelaGateway,
            scoreGateway, simulacaoFinanciamentoGateway);

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        simulacaoRequest = getSimulacaoRequest();
    }

    @Test
    void shouldReturnFinancingInstallmentForALowRiskClient() {
        //Given cliente
        clienteRequest = getClienteRequest(DATA_NASCIMENTO_BAIXO_RISCO, RENDA_BAIXO_RISCO, true);
        final SimulacaoFinanciamentoRequest simulacaoFinanciamentoRequest = SimulacaoFinanciamentoRequest
                .builder()
                .clienteRequest(clienteRequest)
                .simulacaoRequest(simulacaoRequest)
                .build();

        //And parcela and score mocks
        scoreMock = getScoreMock(SCORE_FAIXA_BAIXO, 3, 1.1f);
        doReturn(parcelasMock()).when(parcelaGateway).getAllParcelas();
        doReturn(scoreMock).when(scoreGateway).findByPontuacao(scoreMock.getPontuacao());

        //When call the usecase
        final ParcelaResponseList resultado = handleSimulation.execute(simulacaoFinanciamentoRequest);

        //Then should call the gateways
        Mockito.verify(clienteGateway, Mockito.times(1)).save(modelMapper.map(clienteRequest, Cliente.class));
        Mockito.verify(scoreGateway, Mockito.times(1)).findByPontuacao(scoreMock.getPontuacao());
        Mockito.verify(parcelaGateway, Mockito.times(1)).getAllParcelas();
        Mockito.verify(simulacaoFinanciamentoGateway, Mockito.times(1)).saveAll(Mockito.any());

        //And the results should be correct
        assertEquals(2, resultado.getParcelas().size());
        assertEquals(VINTE_QUATRO_BAIXO_RISCO, resultado.getParcelas().get(0).getValor());
        assertEquals(TRINTA_SEIS_BAIXO_RISCO, resultado.getParcelas().get(1).getValor());
    }

    @Test
    void shouldReturnFinancingInstallmentForAHighRiskMinorClient() {
        //Given cliente
        clienteRequest = getClienteRequest(DATA_NASCIMENTO_ALTO_RISCO_MENOR_IDADE, RENDA_ALTO_RISCO, false);
        final SimulacaoFinanciamentoRequest simulacaoFinanciamentoRequest = SimulacaoFinanciamentoRequest
                .builder()
                .clienteRequest(clienteRequest)
                .simulacaoRequest(simulacaoRequest)
                .build();

        //And parcela and score mocks
        scoreMock = getScoreMock(SCORE_FAIXA_ALTO, 9, 9.34f);
        doReturn(parcelasMock()).when(parcelaGateway).getAllParcelas();
        doReturn(scoreMock).when(scoreGateway).findByPontuacao(scoreMock.getPontuacao());

        //When call the usecase
        final ParcelaResponseList resultado = handleSimulation.execute(simulacaoFinanciamentoRequest);

        //Then should call the gateways
        Mockito.verify(clienteGateway, Mockito.times(1)).save(modelMapper.map(clienteRequest, Cliente.class));
        Mockito.verify(scoreGateway, Mockito.times(1)).findByPontuacao(scoreMock.getPontuacao());
        Mockito.verify(parcelaGateway, Mockito.times(1)).getAllParcelas();
        Mockito.verify(simulacaoFinanciamentoGateway, Mockito.times(1)).saveAll(Mockito.any());

        //And the results should be correct
        assertEquals(2, resultado.getParcelas().size());
        assertEquals(VINTE_QUATRO_ALTO_RISCO, resultado.getParcelas().get(0).getValor());
        assertEquals(TRINTA_SEIS_ALTO_RISCO, resultado.getParcelas().get(1).getValor());
    }

    @Test
    void shouldReturnFinancingInstallmentForAHighRiskOlderClient() {
        //Given cliente
        clienteRequest = getClienteRequest(DATA_NASCIMENTO_ALTO_RISCO_MAIOR_IDADE, RENDA_ALTO_RISCO, false);
        final SimulacaoFinanciamentoRequest simulacaoFinanciamentoRequest = SimulacaoFinanciamentoRequest
                .builder()
                .clienteRequest(clienteRequest)
                .simulacaoRequest(simulacaoRequest)
                .build();

        //And parcela and score mocks
        scoreMock = getScoreMock(SCORE_FAIXA_ALTO, 9, 9.34f);
        doReturn(parcelasMock()).when(parcelaGateway).getAllParcelas();
        doReturn(scoreMock).when(scoreGateway).findByPontuacao(scoreMock.getPontuacao());

        //When call the usecase
        final ParcelaResponseList resultado = handleSimulation.execute(simulacaoFinanciamentoRequest);

        //Then should call the gateways
        Mockito.verify(clienteGateway, Mockito.times(1)).save(modelMapper.map(clienteRequest, Cliente.class));
        Mockito.verify(scoreGateway, Mockito.times(1)).findByPontuacao(scoreMock.getPontuacao());
        Mockito.verify(parcelaGateway, Mockito.times(1)).getAllParcelas();
        Mockito.verify(simulacaoFinanciamentoGateway, Mockito.times(1)).saveAll(Mockito.any());

        //And the results should be correct
        assertEquals(2, resultado.getParcelas().size());
        assertEquals(VINTE_QUATRO_ALTO_RISCO, resultado.getParcelas().get(0).getValor());
        assertEquals(TRINTA_SEIS_ALTO_RISCO, resultado.getParcelas().get(1).getValor());
    }

    @Test
    void shouldReturnFinancingInstallmentForAnAverageRiskClient() {
        //Given cliente
        clienteRequest = getClienteRequest(DATA_NASCIMENTO_MEDIO_RISCO, RENDA_MEDIO_RISCO, false);
        final SimulacaoFinanciamentoRequest simulacaoFinanciamentoRequest = SimulacaoFinanciamentoRequest
                .builder()
                .clienteRequest(clienteRequest)
                .simulacaoRequest(simulacaoRequest)
                .build();

        //And parcela and score mocks
        scoreMock = getScoreMock(SCORE_FAIXA_MEDIO, 7, 5.21f);
        doReturn(parcelasMock()).when(parcelaGateway).getAllParcelas();
        doReturn(scoreMock).when(scoreGateway).findByPontuacao(scoreMock.getPontuacao());
        final Optional<ClienteModel> optionalClienteModel = Optional.of(ClienteModel.builder().nome(NOME).build());
        when(clienteGateway.findClienteModelByCpf(CPF)).thenReturn(optionalClienteModel);

        //When call the usecase
        final ParcelaResponseList resultado = handleSimulation.execute(simulacaoFinanciamentoRequest);

        //Then should call the gateways
        Mockito.verify(scoreGateway, Mockito.times(1)).findByPontuacao(scoreMock.getPontuacao());
        Mockito.verify(parcelaGateway, Mockito.times(1)).getAllParcelas();
        Mockito.verify(simulacaoFinanciamentoGateway, Mockito.times(1)).saveAll(Mockito.any());

        //And the results should be correct
        assertEquals(2, resultado.getParcelas().size());
        assertEquals(VINTE_QUATRO_MEDIO_RISCO, resultado.getParcelas().get(0).getValor());
        assertEquals(TRINTA_SEIS_MEDIO_RISCO, resultado.getParcelas().get(1).getValor());
    }

    @Test
    void shouldThrowsTransactionalExceptionWhenGetScoreFromDatabaseMethodReturnsAnException() {
        //Given cliente
        clienteRequest = getClienteRequest(DATA_NASCIMENTO_ALTO_RISCO_MENOR_IDADE, RENDA_ALTO_RISCO, false);
        final SimulacaoFinanciamentoRequest simulacaoFinanciamentoRequest = SimulacaoFinanciamentoRequest
                .builder()
                .clienteRequest(clienteRequest)
                .simulacaoRequest(simulacaoRequest)
                .build();

        //And parcela and score mocks
        doReturn(parcelasMock()).when(parcelaGateway).getAllParcelas();
        doThrow(mock(NullPointerException.class)).when(scoreGateway).findByPontuacao(1);

        try {
            //When call the usecase
            handleSimulation.execute(simulacaoFinanciamentoRequest);
        } catch (final Exception e) {
            //then
            assertTrue(e instanceof TransactionalException);
        }
    }

    @Test
    void shouldThrowsTransactionalExceptionWhenGetAllParcelasMethodReturnsAnException() {
        //Given cliente
        clienteRequest = getClienteRequest(DATA_NASCIMENTO_MEDIO_RISCO, RENDA_MEDIO_RISCO, false);
        final SimulacaoFinanciamentoRequest simulacaoFinanciamentoRequest = SimulacaoFinanciamentoRequest
                .builder()
                .clienteRequest(clienteRequest)
                .simulacaoRequest(simulacaoRequest)
                .build();

        //And parcela and score mocks
        scoreMock = getScoreMock(SCORE_FAIXA_MEDIO, 7, 5.21f);
        doThrow(mock(NullPointerException.class)).when(parcelaGateway).getAllParcelas();
        when(scoreGateway.findByPontuacao(scoreMock.getPontuacao())).thenReturn(scoreMock);

        try {
            //When call the usecase
            handleSimulation.execute(simulacaoFinanciamentoRequest);
        } catch (final Exception e) {
            //then
            assertTrue(e instanceof TransactionalException);
        }
    }

    @Test
    void shouldThrowsIllegalArgumentExceptionWhenClientsCPFAlreadyRegisteredWithAnotherName() {
        //Given cliente
        clienteRequest = getClienteRequest(DATA_NASCIMENTO_ALTO_RISCO_MENOR_IDADE, RENDA_ALTO_RISCO, false);
        final SimulacaoFinanciamentoRequest simulacaoFinanciamentoRequest = SimulacaoFinanciamentoRequest
                .builder()
                .clienteRequest(clienteRequest)
                .simulacaoRequest(simulacaoRequest)
                .build();

        final Optional<ClienteModel> optionalClienteModel = Optional.of(ClienteModel.builder().nome("OUTRO").build());

        //And parcela and score mocks
        scoreMock = getScoreMock(SCORE_FAIXA_BAIXO, 3, 1.1f);
        doReturn(parcelasMock()).when(parcelaGateway).getAllParcelas();
        doReturn(scoreMock).when(scoreGateway).findByPontuacao(scoreMock.getPontuacao());
        when(clienteGateway.findClienteModelByCpf(CPF)).thenReturn(optionalClienteModel);

        try {
            //When call the usecase
            handleSimulation.execute(simulacaoFinanciamentoRequest);
        } catch (final Exception e) {
            //Then
            assertTrue(e instanceof IllegalArgumentException);
            assertEquals(CPF_JA_REGISTRADO_ERRO, e.getMessage());
        }
    }

    @Test
    void shouldThrowsTransactionalExceptionWhenFindClienteModelByCpfMethodReturnsAnException() {
        //Given cliente
        clienteRequest = getClienteRequest(DATA_NASCIMENTO_BAIXO_RISCO, RENDA_BAIXO_RISCO, true);
        final SimulacaoFinanciamentoRequest simulacaoFinanciamentoRequest = SimulacaoFinanciamentoRequest
                .builder()
                .clienteRequest(clienteRequest)
                .simulacaoRequest(simulacaoRequest)
                .build();

        //And parcela and score mocks
        scoreMock = getScoreMock(SCORE_FAIXA_BAIXO, 3, 1.1f);
        doReturn(parcelasMock()).when(parcelaGateway).getAllParcelas();
        doReturn(scoreMock).when(scoreGateway).findByPontuacao(scoreMock.getPontuacao());
        doThrow(mock(NullPointerException.class)).when(clienteGateway).findClienteModelByCpf(CPF);

        try {
            //When call the usecase
            handleSimulation.execute(simulacaoFinanciamentoRequest);
        } catch (final Exception e) {
            assertTrue(e instanceof TransactionalException);
            assertNotEquals(CPF_JA_REGISTRADO_ERRO, e.getMessage());
        }
    }

    private ClienteRequest getClienteRequest(final LocalDate dataNascimento, final float renda, final boolean possuiImovel) {
        return ClienteRequest.builder()
                .nome(NOME)
                .cpf(CPF)
                .dataNascimento(dataNascimento)
                .telefone(TELEFONE)
                .renda(renda)
                .possuiImovel(possuiImovel)
                .build();
    }

    private SimulacaoRequest getSimulacaoRequest() {
        return SimulacaoRequest.builder()
                .valorEntrada(VALOR_ENTRADA)
                .valorVeiculo(VALOR_VEICULO)
                .build();
    }

    private List<Parcela> parcelasMock() {
        return Arrays.asList(
                Parcela.builder()
                        .quantidade(24)
                        .taxaJuros(0.3f)
                        .build(),
                Parcela.builder()
                        .quantidade(36)
                        .taxaJuros(0.6f)
                        .build());
    }

    private Score getScoreMock(final String faixa, final int pontuacao, final float juros) {
        return Score.builder()
                .faixa(faixa)
                .pontuacao(pontuacao)
                .juros(juros)
                .build();
    }
}
