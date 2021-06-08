package br.com.alura.leilao.test.login;

import java.util.Locale;

import org.openqa.selenium.By;

import com.github.javafaker.Faker;

import br.com.alura.leilao.test.leiloes.LeiloesPage;
import br.com.alura.leilao.test.support.Browser;
import br.com.alura.leilao.test.support.Browsers;

public class LoginPage {

	private Browser browser;

	public LoginPage() {
		this.browser = new Browser();
	}

	public static final String BASE_URL = "http://localhost:8080";
	public static final String AUCTIONS_URL = BASE_URL + "/leiloes";
	public static final String LOGIN_URL = BASE_URL + "/login";
	public static final String LOGIN_ERROR_URL = LOGIN_URL + "?error";
	public static final String loginErrorMessage = "Usuário e senha inválidos.";
	public static final String bidURL = BASE_URL + "/leiloes/2";
	public static final String bidTitle = "Dados do Leilão";

	private Faker fake = new Faker(new Locale("pt-BR"));

	private By botaoEntrar = By.linkText("Entrar");
	private By botaoLogin = By.xpath("//button[contains(text(),\"Login\")]");
	private By formLoginUsuario = By.xpath("//input[@name=\"username\"]");
	private By formLoginSenha = By.xpath("//input[@name=\"password\"]");
	private static By usuarioLogado;

	public void accessLoginPage(Browsers browsers) {
		browser.open(browsers, false);
		browser.navigateTo(AUCTIONS_URL, false);
		if (browser.currentUrlEquals(AUCTIONS_URL))
			browser.click(botaoEntrar);
	}

	public void fillLoginFormData(String username, String password) {
		browser.sendKeys(formLoginUsuario, username != null ? username : fake.name().username());
		browser.sendKeys(formLoginSenha, password != null ? password : fake.internet().slug());
		usuarioLogado = By.xpath("//span[contains(text(),\"" + browser.getValue(formLoginUsuario) + "\")]");
	}

	public LeiloesPage signIn() {
		browser.click(botaoLogin);
		return new LeiloesPage(browser);
	}

	public boolean isLoggedIn() {
		return browser.elementLocated(usuarioLogado);
	}

	public boolean isLoginURL() {
		return browser.currentUrlEquals(LOGIN_URL);
	}

	public boolean isLoginErrorURL() {
		return browser.currentUrlEquals(LOGIN_ERROR_URL);
	}

	public boolean displayErrorMessage() {
		return browser.pageSourceContains(loginErrorMessage);
	}

	public void accessBidePage() {
		browser.navigateTo(bidURL, false);
	}

	public boolean displayBidTitle() {
		return browser.pageSourceContains(bidTitle);
	}

	public void quit() {
		browser.quit();
	}

}
