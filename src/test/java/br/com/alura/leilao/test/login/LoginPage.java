package br.com.alura.leilao.test.login;

import java.util.Locale;

import org.openqa.selenium.By;

import com.github.javafaker.Faker;

import br.com.alura.leilao.test.support.Browser;
import br.com.alura.leilao.test.support.Browsers;

public class LoginPage extends Browser {

	public static final String baseURL = "http://localhost:8080";
	public static final String applicationURL = baseURL + "/leiloes";
	public static final String loginURL = baseURL + "/login";
	public static final String loginErrorURL = loginURL + "?error";
	public static final String loginErrorMessage = "Usuário e senha inválidos.";
	public static final String bidURL = baseURL + "/leiloes/2";
	public static final String bidTitle = "Dados do Leilão";

	private Faker fake = new Faker(new Locale("pt-BR"));

	private By botaoEntrar = By.linkText("Entrar");
	private By botaoLogin = By.xpath("//button[contains(text(),\"Login\")]");
	private By formLoginUsuario = By.xpath("//input[@name=\"username\"]");
	private By formLoginSenha = By.xpath("//input[@name=\"password\"]");
	private static By usuarioLogado;

	public void accessLoginPage(Browsers browsers) {
		open(browsers, false);
		navigateTo(applicationURL, false);
		if (currentUrlEquals(applicationURL))
			click(botaoEntrar);
	}

	public void fillLoginFormData(String username, String password) {
		sendKeys(formLoginUsuario, username != null ? username : fake.name().username());
		sendKeys(formLoginSenha, password != null ? password : fake.internet().slug());
		usuarioLogado = By.xpath("//span[contains(text(),\"" + getValue(formLoginUsuario) + "\")]");
	}

	public void signIn() {
		click(botaoLogin);
	}

	public boolean isLoggedIn() {
		return elementLocated(usuarioLogado);
	}

	public boolean isLoginURL() {
		return currentUrlEquals(loginURL);
	}

	public boolean isLoginErrorURL() {
		return currentUrlEquals(loginErrorURL);
	}

	public boolean displayErrorMessage() {
		return pageSourceContains(loginErrorMessage);
	}

	public void accessBidePage() {
		navigateTo(bidURL, false);

	}

	public boolean displayBidTitle() {
		return pageSourceContains(bidTitle);
	}

}
