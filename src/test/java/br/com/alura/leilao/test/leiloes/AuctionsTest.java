package br.com.alura.leilao.test.leiloes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.javafaker.Faker;

import br.com.alura.leilao.test.login.LoginPage;
import br.com.alura.leilao.test.support.Browsers;

public class AuctionsTest {

	private AuctionsPage auctions;
	private NewAuctionPage newAuction;
	private Faker fake = new Faker(new Locale("pt-BR"));

	@BeforeEach
	public void inicializar() {
		LoginPage login = new LoginPage();
		login.accessLoginPage(Browsers.CHROME);
		login.fillLoginFormData("fulano", "pass");
		this.auctions = login.signIn();
		this.newAuction = auctions.accessNewAuction();
	}

	@AfterEach
	public void finalizar() {
		this.auctions.quit();
	}

	@Test
	public void deveCadastrarNovoLeliao() {
		String inicio = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String nome = fake.commerce().productName();
		String valor = fake.commerce().price(10, 100).replace(",", ".");
		this.auctions = newAuction.registerNewAuction(nome, valor, inicio);
		assertTrue(auctions.isNewAuction(nome, valor, inicio));
	}

	@Test
	public void naoDeveCadastrarNovoLeliaoSemDados() {
		this.auctions = newAuction.registerNewAuction("", "", "");
		assertFalse(this.newAuction.isNewAuctionURL());
		assertTrue(this.auctions.isNewAuctionURL());
		assertTrue(this.newAuction.displayValidationMessage(this.newAuction.errorMessageMinimumName));
		assertTrue(this.newAuction.displayValidationMessage(this.newAuction.errorMessageNameBlank));
		assertTrue(this.newAuction.displayValidationMessage(this.newAuction.errorMessageValueZero));
		assertTrue(this.newAuction.displayValidationMessage(this.newAuction.errorMessageEmptyDate));
	}
}
