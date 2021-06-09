package br.com.alura.leilao.test.leiloes;

import org.openqa.selenium.By;

import br.com.alura.leilao.test.support.Browser;

public class AuctionsPage {

	private Browser browser;

	public AuctionsPage(Browser browser) {
		this.browser = browser;
	}

	public static final String BASE_URL = "http://localhost:8080";
	public static final String AUCTIONS_URL = BASE_URL + "/leiloes";

	private By buttonNewAuction = By.id("novo_leilao_link");
	private By lastAuctionName = By.cssSelector("table > tbody > tr:last-child > td:nth-child(1)");
	private By lastAuctionOpeningDate = By.cssSelector("table > tbody > tr:last-child > td:nth-child(2)");
	private By lastAuctionValue = By.cssSelector("table > tbody > tr:last-child > td:nth-child(3)");

	public boolean isLeiloesURL() {
		return browser.currentUrlEquals(AUCTIONS_URL);
	}

	public NewAuctionPage accessNewAuction() {
		browser.click(buttonNewAuction);
		return new NewAuctionPage(browser);
	}

	public void quit() {
		browser.quit();
	}

	public boolean isNewAuction(String nome, String valor, String inicio) {
		boolean _registeredName = browser.getText(lastAuctionName).equals(nome);
		boolean _registeredValue = browser.getText(lastAuctionValue).contentEquals(valor);
		boolean _registeredOpeningDate = browser.getText(lastAuctionOpeningDate).equals(inicio);
		return _registeredName && _registeredOpeningDate && _registeredValue;
	}

	public boolean isNewAuctionURL() {
		return browser.currentUrlEquals(AUCTIONS_URL);
	}

}
