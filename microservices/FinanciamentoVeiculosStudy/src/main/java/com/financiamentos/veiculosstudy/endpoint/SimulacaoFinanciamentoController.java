package com.financiamentos.veiculosstudy.endpoint;

import com.financiamentos.veiculosstudy.entity.ParcelaResponseList;
import com.financiamentos.veiculosstudy.entity.endpoint.Response;
import com.financiamentos.veiculosstudy.entity.endpoint.SimulacaoFinanciamentoRequest;
import com.financiamentos.veiculosstudy.usecase.HandleSimulation;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static net.logstash.logback.argument.StructuredArguments.value;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class SimulacaoFinanciamentoController {

    private final HandleSimulation handleSimulation;

    @ApiOperation(value = "Returns a financial simulation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 422, message = "Business Error"),
            @ApiResponse(code = 500, message = "Internal Server Error")})
    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/simular")
    public ResponseEntity<Response<ParcelaResponseList>> simularFinanciamento(@RequestBody final SimulacaoFinanciamentoRequest simulacaoFinanciamentoRequest) {
        log.debug("Simulacao requested: {}", value("simulacaoFinanciamentoRequest", simulacaoFinanciamentoRequest));

        try {
            return ResponseEntity.ok(new Response<>(handleSimulation.execute(simulacaoFinanciamentoRequest)));

        } catch (final IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().body(new Response<>(Collections.singletonList(e.getMessage())));

        } catch (final Exception e) {
            return ResponseEntity.status(500).body(new Response<>(Collections.singletonList(e.getMessage())));
        }
    }
}
