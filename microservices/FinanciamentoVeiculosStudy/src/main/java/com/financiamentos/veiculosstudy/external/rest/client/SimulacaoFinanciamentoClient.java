package com.financiamentos.veiculosstudy.external.rest.client;

import com.financiamentos.veiculosstudy.entity.endpoint.SimulacaoFinanciamentoRequest;
import com.financiamentos.veiculosstudy.external.rest.client.config.SimulacaoFinanciamentoClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${feign.rest.simulacao.name}", url = "${feign.rest.simulacao.url}", configuration = SimulacaoFinanciamentoClientConfig.class)
public interface SimulacaoFinanciamentoClient {
    @PostMapping(value = "/v1/simular")
    String simular(@RequestBody final SimulacaoFinanciamentoRequest request);
}
