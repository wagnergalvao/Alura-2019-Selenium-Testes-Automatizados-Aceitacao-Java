package br.com.alura.leilao.lance;

import br.com.alura.leilao.BasePage;
import br.com.alura.leilao.support.Browser;

public class AuctionBidPage extends BasePage{

	public AuctionBidPage(Browser browser) {
		super(browser);
	}

	public static final String AUCTION_BID_URL = BASE_URL + "/leiloes/2";
	public static final String AuctionBidTitle = "Dados do Leilão";

	public void accessAuctionBidePage() {
		browser.navigateTo(AUCTION_BID_URL, false);
	}

	public boolean isAuctionBidURL() {
		return browser.currentUrlEquals(AUCTION_BID_URL);
	}

	public boolean displayAuctionBidTitle() {
		return browser.pageSourceContains(AuctionBidTitle);
	}

}
