package br.com.alura.leilao.test.login;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.github.javafaker.Faker;

import br.com.alura.leilao.test.support.Browser;
import br.com.alura.leilao.test.support.Browsers;

public class Login extends Browser {

	private Faker fake = new Faker(new Locale("pt-BR"));
	private static Browser login = new Browser();
	private static String _username;
	private static String _password;

	private String _invalidos = "Usuário e senha inválidos.";
	private By botaoEntrar = By.linkText("Entrar");
	private By botaoLogin = By.xpath("//button[contains(text(),\"Login\")]");
	private By formLoginUsuario = By.xpath("//input[@name=\"username\"]");
	private By formLoginSenha = By.xpath("//input[@name=\"password\"]");
	private By usuarioLogado;

	@BeforeEach
	public void inicializar() {
		login.open(Browsers.CHROME, false);
		login.navigateTo("http://localhost:8080/leiloes", false);
		_username = fake.name().username();
		_password = fake.internet().slug();
		usuarioLogado = By.xpath("//span[contains(text(),\"" + _username + "\")]");
		assertTrue(login.currentUrlEquals("http://localhost:8080/leiloes"));
	}

	@AfterEach
	public void finalizar() {
		login.quit();
	}

	@Test
	public void deveEfetuarLoginComSucesso() {
		login.click(botaoEntrar);
		assertTrue(login.currentUrlEquals("http://localhost:8080/login"));
		_username = "fulano";
		usuarioLogado = By.xpath("//span[contains(text(),\"" + _username + "\")]");
		login.sendKeys(formLoginUsuario, _username);
		_password = "pass";
		login.sendKeys(formLoginSenha, _password);
		login.click(botaoLogin);
		assertFalse(login.currentUrlContains("/login"));
		assertEquals(_username, login.getText(usuarioLogado));
	}

	@Test
	public void naoDeveEfetuarLoginComDadosInvalidos() {
		login.click(botaoEntrar);
		assertTrue(login.currentUrlEquals("http://localhost:8080/login"));
		login.sendKeys(formLoginUsuario, _username);
		login.sendKeys(formLoginSenha, _password);
		login.click(botaoLogin);
		assertTrue(login.currentUrlContains("/login?error"));
		assertTrue(login.pageSourceContains(_invalidos));
		assertFalse(login.elementLocated(usuarioLogado));
	}

	@Test
	public void naoDeveEfetuarLoginComDadosVazios() {
		login.click(botaoEntrar);
		assertTrue(login.currentUrlEquals("http://localhost:8080/login"));
		login.click(botaoLogin);
		assertTrue(login.currentUrlContains("/login?error"));
		assertTrue(login.pageSourceContains(_invalidos));
		assertFalse(login.elementLocated(usuarioLogado));
	}

	@Test
	public void naoAcessarPaginaRestritaDeslogado() {
		login.navigateTo("http://localhost:8080/leiloes/2", false);
		assertTrue(login.currentUrlEquals("http://localhost:8080/login"));
		assertFalse(login.elementLocated(usuarioLogado));
	}
}
