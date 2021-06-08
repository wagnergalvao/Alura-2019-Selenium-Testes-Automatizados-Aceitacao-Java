package br.com.alura.leilao.test.login;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.test.support.Browsers;

public class LoginTest {

	private LoginPage login = new LoginPage();

	@BeforeEach
	public void inicializar() {
		login.accessLoginPage(Browsers.CHROME);
		assertTrue(login.isLoginURL());
	}

	@AfterEach
	public void finalizar() {
		login.quit();
	}

	@Test
	public void deveEfetuarLoginComSucesso() {
		login.fillLoginFormData("fulano", "pass");
		login.signIn();
		assertFalse(login.isLoginURL());
		assertTrue(login.isLoggedIn());
	}

	@Test
	public void naoDeveEfetuarLoginComDadosInvalidos() {
		login.fillLoginFormData(null, null);
		login.signIn();
		assertTrue(login.isLoginErrorURL());
		assertTrue(login.displayErrorMessage());
	}

	@Test
	public void naoDeveEfetuarLoginComDadosVazios() {
		login.signIn();
		assertTrue(login.isLoginErrorURL());
		assertTrue(login.displayErrorMessage());
	}

	@Test
	public void naoAcessarPaginaRestritaDeslogado() {
		login.accessBidePage();
		assertTrue(login.isLoginURL());
		assertFalse(login.displayBidTitle());
	}
}
