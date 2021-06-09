package br.com.alura.leilao.leiloes;

import org.openqa.selenium.By;

import br.com.alura.leilao.BasePage;
import br.com.alura.leilao.support.Browser;

public class NewAuctionPage extends BasePage {


	public NewAuctionPage(Browser browser) {
		super(browser);
	}

	public static final String NEW_AUCTION_URL = BASE_URL + "/new";

	public String errorMessageMinimumName = "minimo 3 caracteres";
	public String errorMessageNameBlank = "não deve estar em branco";
	public String errorMessageValueZero = "deve ser um valor maior de 0.1";
	public String errorMessageInvalidValue = "Failed to convert property value of type java.lang.String to required type java.math.BigDecimal for property valorInicial; nested exception is java.lang.NumberFormatException";
	public String errorMessageEmptyDate = "deve ser uma data no formato dd/MM/yyyy";

	private By fieldAuctionName = By.id("nome");
	private By fieldInitialValue = By.id("valorInicial");
	private By fieldOpeningDate = By.id("dataAbertura");
	private By buttonSubmit = By.id("button-submit");

	public AuctionsPage registerNewAuction(String auctionName, String initialValue, String openingDate) {
		browser.sendKeys(fieldAuctionName, auctionName);
		browser.sendKeys(fieldInitialValue, initialValue);
		browser.sendKeys(fieldOpeningDate, openingDate);
		browser.submit(buttonSubmit);
		return new AuctionsPage(browser);
	}

	public boolean isNewAuctionURL() {
		return browser.currentUrlEquals(NEW_AUCTION_URL);
	}

	public boolean displayValidationMessage(String errorMessage) {
		return browser.pageSourceContains(errorMessage);
	}

}
