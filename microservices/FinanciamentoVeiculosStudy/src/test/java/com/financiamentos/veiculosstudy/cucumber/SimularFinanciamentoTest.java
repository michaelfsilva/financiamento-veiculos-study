package com.financiamentos.veiculosstudy.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/features/simular_financiamento.feature", tags = "~@SimularFinanciamento",
        glue = "com.financiamentos.veiculosstudy.cucumber.steps", monochrome = true, snippets = SnippetType.CAMELCASE)
public class SimularFinanciamentoTest {

}
