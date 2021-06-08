package br.com.alura.leilao.test.leiloes;

import br.com.alura.leilao.test.support.Browser;

public class NovoLeilao {

	private Browser browser;

	public NovoLeilao(Browser browser) {
		this.browser = browser;
	}

	public void quit() {
		browser.quit();
	}

}
