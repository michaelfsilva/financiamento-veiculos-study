package com.financiamentos.veiculosstudy.external.rest.client.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class SimulacaoFinanciamentoClientConfig {
    @Value("${feign.rest.simulacao.headers.username}")
    private String username;

    @Value("${feign.rest.simulacao.headers.password}")
    private String password;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(username, password);
    }
}
