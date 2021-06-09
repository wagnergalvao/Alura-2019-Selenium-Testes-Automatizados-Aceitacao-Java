package br.com.alura.leilao.leiloes;

import org.openqa.selenium.By;

import br.com.alura.leilao.BasePage;
import br.com.alura.leilao.support.Browser;

public class AuctionsPage extends BasePage{

	public AuctionsPage(Browser browser) {
		super(browser);
	}

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
