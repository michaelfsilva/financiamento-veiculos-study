package com.financiamentos.veiculosstudy.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/features/simulacao.feature", tags = "@SimulacaoTest",
        glue = "com.financiamentos.veiculosstudy.cucumber.steps", monochrome = true, dryRun = false, snippets = SnippetType.CAMELCASE)
public class SimulacaoTest {
}
