package br.com.alura.leilao.test.login;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.github.javafaker.Faker;

import br.com.alura.leilao.test.support.Browser;
import br.com.alura.leilao.test.support.Browsers;
import br.com.alura.leilao.test.support.Expected_Conditions;

public class Login extends Browser {

	private Faker fake = new Faker(new Locale("pt-BR"));
	private static Browser login = new Browser();
	private static String _username;
	private static String _password;

	private By botaoEntrar = By.linkText("Entrar");
	private By botaoLogin = By.linkText("Login");
	private By tituloLogin = By.xpath("//h1[contains(text(),\"Login\")]");
	private By formLogin = By.id("form-login");
	private By formLoginUsuario = By.xpath("//input[@name=\"username\"]");
	private By formLoginSenha = By.xpath("//input[@name=\"password\"]");

	@BeforeEach
	public void inicializar() {
		login.open(Browsers.CHROME, false);
		login.navigateTo("http://localhost:8080/leiloes", false);
		login.waitUntilElement(botaoEntrar, Expected_Conditions.ELEMENTTOBECLICKABLE);
		_username = fake.name().username();
		_password = fake.internet().slug();
	}

	@AfterEach
	public void finalizar() {
		login.quit();
	}

	@Test
	public void deveAcessarAPaginaLoginPeloBotaoEntrar() {
		login.click(botaoEntrar);
		assertEquals("Login", login.getText(tituloLogin));
	}

	@Test
	public void devePreencherOUsuario() {
		deveAcessarAPaginaLoginPeloBotaoEntrar();
		login.sendKeys(formLoginUsuario, _username);
		assertEquals(_username, login.getValue(formLoginUsuario));
	}

	@Test
	public void devePreencherASenha() {
		deveAcessarAPaginaLoginPeloBotaoEntrar();
		login.sendKeys(formLoginSenha, _password);
		assertEquals(_password, login.getValue(formLoginSenha));
	}

	@Test
	public void deveEfetuarLoginComSucesso() {
		deveAcessarAPaginaLoginPeloBotaoEntrar();
		_username = "fulano";
		devePreencherOUsuario();
		_password = "pass";
		devePreencherASenha();
	}
}
