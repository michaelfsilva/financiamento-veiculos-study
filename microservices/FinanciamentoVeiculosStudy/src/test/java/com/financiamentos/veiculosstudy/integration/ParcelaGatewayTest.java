package com.financiamentos.veiculosstudy.integration;

import com.financiamentos.veiculosstudy.entity.Parcela;
import com.financiamentos.veiculosstudy.external.database.ParcelaGatewayImpl;
import com.financiamentos.veiculosstudy.external.database.model.ParcelaModel;
import com.financiamentos.veiculosstudy.external.database.repository.ParcelaRepository;
import com.financiamentos.veiculosstudy.external.gateway.ParcelaGateway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.TransactionalException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional
class ParcelaGatewayTest {
    @Autowired
    ParcelaGateway parcelaGateway;

    @Autowired
    ParcelaRepository parcelaRepository;

    ParcelaRepository parcelaRepositoryMock = mock(ParcelaRepository.class);
    ParcelaGateway parcelaGatewayMock = new ParcelaGatewayImpl(parcelaRepositoryMock);

    @Test
    void shouldFindAllParcelas() {
        //Given added parcelas to database
        parcelaRepository.saveAll(Arrays.asList(
                ParcelaModel.builder()
                        .quantidade(24)
                        .taxaJuros(0.3f)
                        .build(),
                ParcelaModel.builder()
                        .quantidade(36)
                        .taxaJuros(0.5f)
                        .build()));

        //When call the gateway
        final List<Parcela> parcelas = parcelaGateway.getAllParcelas();

        //Then should get correct values
        assertEquals(2, parcelas.size());
        assertEquals(24, parcelas.get(0).getQuantidade());
        assertEquals(0.3f, parcelas.get(0).getTaxaJuros());
        assertEquals(36, parcelas.get(1).getQuantidade());
        assertEquals(0.5f, parcelas.get(1).getTaxaJuros());
    }

    @Test
    void shouldGetExceptionIfParcelasNotFound() {

        when(parcelaGatewayMock.getAllParcelas()).thenThrow(TransactionalException.class);

        assertThrows(Exception.class, () -> {
            parcelaGatewayMock.getAllParcelas();
        });
    }
}
