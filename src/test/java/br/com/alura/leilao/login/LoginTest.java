package br.com.alura.leilao.login;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.lance.AuctionBidPage;
import br.com.alura.leilao.support.Browsers;

public class LoginTest {

	private LoginPage login = new LoginPage();
	private AuctionBidPage auctionBidPage;

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
		this.auctionBidPage = new AuctionBidPage(null); 
		assertTrue(login.isLoginURL());
		assertFalse(auctionBidPage.displayAuctionBidTitle());
	}
}
