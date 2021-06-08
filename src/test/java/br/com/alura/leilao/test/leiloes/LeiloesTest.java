package br.com.alura.leilao.test.leiloes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.test.login.LoginPage;
import br.com.alura.leilao.test.support.Browsers;

public class LeiloesTest {

	private LeiloesPage leiloes;

	@AfterEach
	public void finalizar() {
		leiloes.quit();
	}

	@Test
	public void deveCadastrarNovoLeliao() {
		LoginPage login = new LoginPage();
		login.accessLoginPage(Browsers.CHROME);
		login.fillLoginFormData("fulano", "pass");
		leiloes = login.signIn();
		NovoLeilao novoLeilao = leiloes.accessNewAuction();
	}
}
