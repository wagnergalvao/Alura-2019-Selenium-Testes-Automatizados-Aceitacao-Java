package br.com.alura.leilao.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.test.support.Browser;
import br.com.alura.leilao.test.support.Browsers;

public class HelloWorldSelenium {

	@BeforeEach
	
	@Test
	public void Hello() {
		Browser hello = new Browser();
		hello.open(Browsers.CHROME, false);
		hello.navigateTo("http://localhost:8080/leiloes", false);
		hello.quit();
	}
}
