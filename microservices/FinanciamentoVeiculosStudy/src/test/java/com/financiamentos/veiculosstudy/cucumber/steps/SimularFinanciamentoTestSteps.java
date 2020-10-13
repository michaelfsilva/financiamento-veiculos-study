package com.financiamentos.veiculosstudy.cucumber.steps;

import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SimularFinanciamentoTestSteps {

    private static final String VALOR_12_PARCELAS = "11440.879";
    private static final String VALOR_24_PARCELAS = "7627.002";
    private static final String VALOR_36_PARCELAS = "6699.545";
    private static final String VALOR_40_PARCELAS = "6588.705";
    private static final String VALOR_60_PARCELAS = "6863.6885";
    private static final String CHROMEDRIVER_PATH = "src/main/resources/drivers/chromedriver";
    private static final String CHROMEDRIVER_PROPERTY = "webdriver.chrome.driver";
    private static final String MARCAS = "marcas";
    private static final String MODELOS = "modelos";
    private static final String ANOS = "anos";

    @Value("${test.frontend.url}")
    String url;

    private WebDriver driver;

    @Dado("^que estou acessando a aplicacao$")
    public void queEstouAcessandoAAplicacao() {
        final String chromeDriver = new File(CHROMEDRIVER_PATH).getAbsolutePath();
        System.setProperty(CHROMEDRIVER_PROPERTY, chromeDriver);
        driver = new ChromeDriver();
        driver.get(url);
    }

    @Dado("^insiro o nome \"([^\"]*)\", cpf \"([^\"]*)\", data de nascimento \"([^\"]*)\", telefone \"([^\"]*)\" e renda \"([^\"]*)\" do cliente$")
    public void insiroONomeCpfDataDeNascimentoTelefoneERendaDoCliente(final String arg1, final String arg2, final String arg3, final String arg4, final String arg5) {
        driver.findElement(By.name("nomeCliente")).sendKeys(arg1);
        final WebElement cpfField = driver.findElement(By.name("cpf"));
        cpfField.sendKeys(arg2);
        cpfField.sendKeys(Keys.TAB);
        driver.findElement(By.name("dataNascimento")).sendKeys(arg3);
        driver.findElement(By.name("telefone")).sendKeys(arg4);
        driver.findElement(By.name("renda")).sendKeys(arg5);
        driver.findElement(By.id("proximo")).click();
    }

    @Dado("^escolho o veiculo e insiro o valor de entrada$")
    public void EscolhoOVeiculoEInsiroOValorDeEntrada() {
        driver.findElement(By.id(MARCAS)).click();
        await().atMost(2, TimeUnit.SECONDS).until(selectVehicleInformation(MARCAS, "6"));

        driver.findElement(By.id(MODELOS)).click();
        await().atMost(2, TimeUnit.SECONDS).until(selectVehicleInformation(MODELOS, "6293"));

        driver.findElement(By.id(ANOS)).click();
        await().atMost(2, TimeUnit.SECONDS).until(selectVehicleInformation(ANOS, "2013-1"));

        await().atMost(5, TimeUnit.SECONDS).until(getVehicleValue());

        driver.findElement(By.name("valorEntrada")).sendKeys("44000.00");
    }

    @Quando("^clico em simular$")
    public void ClicoEmSimular() {
        driver.findElement(By.id("simular")).click();
    }

    @Entao("^a pagina retorna o resultado da simulacao$")
    public void APaginaRetornaOResultadoDaSimulacao() {
        final WebElement comboParcelas = driver.findElement(By.id("parcelas"));
        comboParcelas.click();
        final Select selectBoxParcela = new Select(comboParcelas);

        assertDoesNotThrow(() -> selectBoxParcela.selectByValue(VALOR_12_PARCELAS));
        assertDoesNotThrow(() -> selectBoxParcela.selectByValue(VALOR_24_PARCELAS));
        assertDoesNotThrow(() -> selectBoxParcela.selectByValue(VALOR_36_PARCELAS));
        assertDoesNotThrow(() -> selectBoxParcela.selectByValue(VALOR_40_PARCELAS));
        assertDoesNotThrow(() -> selectBoxParcela.selectByValue(VALOR_60_PARCELAS));
    }

    private Callable<Boolean> selectVehicleInformation(final String type, final String value) {
        return () -> {
            try {
                final Select selectBoxMarcas = new Select(driver.findElement(By.id(type)));
                selectBoxMarcas.selectByValue(value);
                return true;
            } catch (final Exception e) {
                return false;
            }
        };
    }

    private Callable<Boolean> getVehicleValue() {
        return () -> {
            try {
                driver.findElement(By.name("valorVeiculo")).getAttribute("ng-reflect-model");
                return true;
            } catch (final Exception e) {
                return false;
            }
        };
    }

    @After(value = "@SimularFinanciamento")
    public void closeBrowser() {
        try {
            driver.quit();
        } catch (final Exception e) {
            throw new PendingException();
        }
    }
}
