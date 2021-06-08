package br.com.alura.leilao.test.leiloes;

import org.openqa.selenium.By;

import br.com.alura.leilao.test.support.Browser;

public class LeiloesPage {

	private Browser browser;

	public LeiloesPage(Browser browser) {
		this.browser = browser;
	}

	public static final String BASE_URL = "http://localhost:8080";
	public static final String AUCTIONS_URL = BASE_URL + "/leiloes";
	public static final String NEW_AUCTION_URL = BASE_URL + "/new";
	
	private By botaoNovoLeilao = By.id("novo_leilao_link");

	public boolean isLeiloesURL() {
		return browser.currentUrlEquals(AUCTIONS_URL);
	}

	public NovoLeilao accessNewAuction() {
		browser.click(botaoNovoLeilao);
		return new NovoLeilao(browser);
	}

	public void quit() {
		browser.quit();
	}

}
