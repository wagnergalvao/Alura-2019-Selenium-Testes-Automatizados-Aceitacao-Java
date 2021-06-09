package br.com.alura.leilao;

import br.com.alura.leilao.support.Browser;

public class BasePage {

	protected Browser browser;

	public static final String BASE_URL = "http://localhost:8080";
	public static final String AUCTIONS_URL = BASE_URL + "/leiloes";

	public BasePage(Browser browser) {
		if (browser == null) {
			this.browser = new Browser();
		} else {
			this.browser = browser;
		}
	}

	public void quit() {
		browser.quit();
	}

}
