package br.com.alura.leilao.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.test.support.Browser;

public class HelloWorldSelenium {

	@BeforeEach
	
	@Test
	public void Hello() {
		Browser browser = new Browser();
		browser.open("chrome", false);
		browser.navigateTo("http://localhost:8080/leiloes", false);
		browser.quit();
	}
}
