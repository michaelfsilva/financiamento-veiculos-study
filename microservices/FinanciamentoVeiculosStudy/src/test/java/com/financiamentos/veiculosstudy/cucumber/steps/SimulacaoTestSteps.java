package com.financiamentos.veiculosstudy.cucumber.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financiamentos.veiculosstudy.endpoint.SimulacaoFinanciamentoController;
import com.financiamentos.veiculosstudy.entity.ParcelaResponse;
import com.financiamentos.veiculosstudy.entity.ParcelaResponseList;
import com.financiamentos.veiculosstudy.entity.endpoint.ClienteRequest;
import com.financiamentos.veiculosstudy.entity.endpoint.SimulacaoFinanciamentoRequest;
import com.financiamentos.veiculosstudy.entity.endpoint.SimulacaoRequest;
import com.financiamentos.veiculosstudy.usecase.HandleSimulation;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.ws.rs.core.Application;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ContextConfiguration(
        loader = SpringBootContextLoader.class,
        classes = Application.class
)
@WebMvcTest(SimulacaoFinanciamentoController.class)
@WithMockUser(username = "bff", password = "veiculos")
public class SimulacaoTestSteps {
    @Autowired
    private MockMvc mockMvc;
    private final HandleSimulation handleSimulation = mock(HandleSimulation.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private SimulacaoFinanciamentoRequest simulacaoFinanciamentoRequest;
    //TODO change type to ParcelaResponseList
    private String parcelaResponseList;

    @Given("^payload filled with vehicle value (\\d+) and value of entry (\\d+), birth date (.*?), if has own property (\\d+) and income (\\d+)$")
    public void payload_filled_with_vehicle_value_and_value_of_entry_age_if_has_own_property_and_income(
            final float valorVeiculo, final float valorEntrada, final String dataDeNascimento, final boolean possuiImovel, final float renda) throws Throwable {

        final ClienteRequest clienteRequest = new ClienteRequest("Cucumber Test", "123.456.789-00",
                convertStringtoLocalDate(dataDeNascimento), "+55 19 98765-4321", renda, possuiImovel);
        final SimulacaoRequest simulacaoRequest = new SimulacaoRequest(valorEntrada, valorVeiculo);
        simulacaoFinanciamentoRequest = new SimulacaoFinanciamentoRequest(clienteRequest, simulacaoRequest);
    }

    @When("^I request a simulation$")
    public void i_request_a_simulation() throws Throwable {
        when(handleSimulation.execute(any())).thenReturn(getParcelaResponseList());

        //TODO fix mocks
        final MvcResult result = mockMvc.perform(post("http://localhost:8080/v1/simular")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(simulacaoFinanciamentoRequest)))
                .andReturn();
        parcelaResponseList = result.getResponse().getContentAsString();
    }

    @Then("^returns the simulation data for each installment option$")
    public void returns_the_simulation_data_for_each_installment_option() throws Throwable {
        //TODO change "" to getParcelaResponseList()
        assertEquals("", parcelaResponseList);
    }

    private LocalDate convertStringtoLocalDate(final String date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(date, formatter);
    }

    private ParcelaResponseList getParcelaResponseList() {
        return ParcelaResponseList.builder()
                .parcelas(Arrays.asList(
                        ParcelaResponse.builder().meses(12).valor(1367.89f).build(),
                        ParcelaResponse.builder().meses(24).valor(846.67f).build(),
                        ParcelaResponse.builder().meses(48).valor(600.12f).build()))
                .build();
    }

}
