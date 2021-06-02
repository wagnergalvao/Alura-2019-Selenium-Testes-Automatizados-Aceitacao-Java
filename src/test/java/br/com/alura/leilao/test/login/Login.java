package br.com.alura.leilao.test.login;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.github.javafaker.Faker;

import br.com.alura.leilao.test.support.Browser;
import br.com.alura.leilao.test.support.EnumExpectedConditions;

public class Login extends Browser {

	private Faker fake = new Faker(new Locale("pt-BR"));
	private static Browser login = new Browser();

	By botaoEntrar = By.linkText("Entrar");
	By botaoLogin = By.linkText("Login");
	By tituloLogin = By.xpath("//h1[contains(text(),\"Login\")]");

	@BeforeEach
	public void inicializar() {
		login.open("chrome", false);
		login.navigateTo("http://localhost:8080/leiloes", false);
		login.waitUntilElement(botaoEntrar, EnumExpectedConditions.ELEMENTTOBECLICKABLE);
	}

	@AfterEach
	public void finalizar() {
		login.quit();
	}

	@Test
	public void deveAcessarAPaginaLoginPeloBotaoEntrar() {
		login.click(botaoEntrar);
		assertEquals("Login", browser.findElement(tituloLogin).getText());
	}

	@Test
	public void deveEfetuarLoginComSucesso() {
		deveAcessarAPaginaLoginPeloBotaoEntrar();
	}
}
