package com.financiamentos.veiculosstudy.unit.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.financiamentos.veiculosstudy.endpoint.SimulacaoFinanciamentoController;
import com.financiamentos.veiculosstudy.entity.ParcelaResponse;
import com.financiamentos.veiculosstudy.entity.ParcelaResponseList;
import com.financiamentos.veiculosstudy.entity.endpoint.SimulacaoFinanciamentoRequest;
import com.financiamentos.veiculosstudy.usecase.HandleSimulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(SimulacaoFinanciamentoController.class)
@WithMockUser(username = "bff", password = "veiculos")
class SimulacaoFinanciamentoControllerTest {

    private static final String PARCELAS_LIST = "{\"parcelas\":[{\"meses\":12,\"valor\":1367.89},{\"meses\":24,\"valor\":846.67},{\"meses\":48,\"valor\":600.12}]}";
    private static final String ENDPOINT = "/v1/simular";
    private static final String WRONG_ENDPOINT = "/v2/financiar";

    private ObjectMapper objectMapper;
    private final SimulacaoFinanciamentoRequest simulacaoFinanciamentoRequest = mock(SimulacaoFinanciamentoRequest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HandleSimulation handleSimulation;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        allowMockConvertToJson();
    }

    @Test
    void shouldReturnParcelasResponseIfRequestHasAllTheRightValues() throws Exception {
        //given
        when(handleSimulation.execute(any())).thenReturn(getParcelaResponseList());

        //when
        final MvcResult result = mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(simulacaoFinanciamentoRequest)))
                .andReturn();

        //then
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

        //and
        assertTrue(result.getResponse().getContentAsString().contains(PARCELAS_LIST));
    }

    @Test
    void shouldReturnNotFoundErrorIfEndpointIsWrong() throws Exception {
        //when
        final MvcResult result = mockMvc.perform(post(WRONG_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(simulacaoFinanciamentoRequest)))
                .andReturn();

        //then
        assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
    }

    @Test
    void shouldReturnNotOKIfUseCaseReturnsAnException() throws Exception {
        //given
        when(handleSimulation.execute(any())).thenThrow(mock(IllegalArgumentException.class));

        //when
        final MvcResult result = mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(simulacaoFinanciamentoRequest)))
                .andReturn();

        //then
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY.value(), result.getResponse().getStatus());
    }

    @Test
    void shouldReturn500AsStatusIfUseCaseReturnsAnException() throws Exception {
        //given
        when(handleSimulation.execute(any())).thenThrow(mock(NullPointerException.class));

        //when
        final MvcResult result = mockMvc.perform(post(ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(simulacaoFinanciamentoRequest)))
                .andReturn();

        //then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getResponse().getStatus());
    }

    private ParcelaResponseList getParcelaResponseList() {
        return ParcelaResponseList.builder()
                .parcelas(Arrays.asList(
                        ParcelaResponse.builder().meses(12).valor(1367.89f).build(),
                        ParcelaResponse.builder().meses(24).valor(846.67f).build(),
                        ParcelaResponse.builder().meses(48).valor(600.12f).build()))
                .build();
    }

    private void allowMockConvertToJson() {
        objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            @Override
            public boolean hasIgnoreMarker(final AnnotatedMember m) {
                return super.hasIgnoreMarker(m) || m.getName().contains("Mockito");
            }
        });
    }
}
