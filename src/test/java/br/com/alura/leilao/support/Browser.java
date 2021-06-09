package br.com.alura.leilao.support;

import java.text.Normalizer;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Wagner.Galvão
 *
 */
@SuppressWarnings("unchecked")
public class Browser {

	public static WebDriver browser;
	public static Capabilities _capabilities;
	public static Map<String, Long> _timeouts;
	public static WebDriverWait _wait;
	protected String _validate;

	/**
	 * click on the element
	 * 
	 * @param By element
	 */
	public void click(By element) {
		waitUntilElement(element, Expected_Conditions.ELEMENTTOBECLICKABLE);
		browser.findElement(element).click();
	}

	/**
	 * Validate part of the URL
	 * 
	 * @param String text
	 * 
	 * @return boolean
	 */
	public boolean currentUrlContains(String text) {
		waitPageLoad();
		return browser.getCurrentUrl().contains(text);
	}

	/**
	 * Validate URL
	 * 
	 * @param String url
	 * 
	 * @return boolean
	 */
	public boolean currentUrlEquals(String url) {
		waitPageLoad();
		return browser.getCurrentUrl().equals(url);
	}

	/**
	 * Validate URL ignoring Case
	 * 
	 * @param String url
	 * 
	 * @return boolean
	 */
	public boolean currentUrlEqualsIgnoreCase(String url) {
		waitPageLoad();
		return browser.getCurrentUrl().equalsIgnoreCase(url);
	}

	/**
	 * Validate presence of element located
	 * 
	 * @param By element
	 * 
	 * @return boolean
	 */
	public boolean elementLocated(By element) {
		return ExpectedConditions.presenceOfAllElementsLocatedBy(element).apply(browser) != null;
	}

	/**
	 * @return Browser Name
	 */
	public String getBrowserName() {
		return _capabilities.getBrowserName();
	}

	/**
	 * Get Browser Capabilities
	 * 
	 * @return Browser Capabilities
	 */
	public Capabilities getCapabilities() {
		return ((RemoteWebDriver) browser).getCapabilities();
	}

	/**
	 * get text from element
	 * 
	 * @param By element
	 * 
	 * @return String
	 */
	public String getText(By element) {
		waitUntilElement(element, Expected_Conditions.PRESENCEOFELEMENTLOCATED);
		return browser.findElement(element).getText();
	}

	/**
	 * Get Browser Timeouts
	 * 
	 * @return Browser Timeouts
	 */
	public Map<String, Long> getTimeouts() {
		return (Map<String, Long>) _capabilities.getCapability("timeouts");
	}

	/**
	 * get value from element
	 * 
	 * @param By element
	 * 
	 * @return String
	 */
	public String getValue(By element) {
		waitUntilElement(element, Expected_Conditions.PRESENCEOFELEMENTLOCATED);
		return browser.findElement(element).getAttribute("value");
	}

	/**
	 * Open the URL, wait page load and validates SSL if acceptInsecureCerts true
	 * 
	 * @param url
	 * @param acceptInsecureCerts: true Skip Secure Sockets Layer Validation false
	 *                             Maintain Secure Sockets Layer validation
	 */
	public void navigateTo(String url, boolean acceptInsecureCerts) {
		browser.navigate().to(url);
		waitPageLoad();
		if (acceptInsecureCerts)
			validateSSL();
	}

	/**
	 * Remove accents from text
	 * 
	 * @param String text
	 * 
	 * @return String
	 */
	public String noAccent(String text) {
		return Normalizer.normalize(text, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
	}

	/**
	 * Open the browser
	 * 
	 * @param browserName
	 * @param acceptInsecureCerts: true Skip Secure Sockets Layer Validation false
	 *                             Maintain Secure Sockets Layer validation
	 * 
	 * @return Browser instance
	 */
	public WebDriver open(Browsers browsers, boolean acceptInsecureCerts) {
		browser = browsers.browserInstance(_timeouts, acceptInsecureCerts);
		browser.manage().window().maximize();
		_capabilities = getCapabilities();
		_timeouts = getTimeouts();
		return browser;
	}

	/**
	 * Validate text on page
	 * 
	 * @param String text
	 * 
	 * @return boolean
	 */
	public boolean pageSourceContains(String text) {
		waitPageLoad();
		return browser.getPageSource().contains(text);
	}

	/**
	 * Close Browser
	 * 
	 * @param browser
	 */
	public void quit() {
		if (browser != null) {
			browser.quit();
		}
	}

	/**
	 * write on the element
	 * 
	 * @param By     element
	 * 
	 * @param String text
	 */
	public void sendKeys(By element, String text) {
		waitUntilElement(element, Expected_Conditions.ELEMENTTOBECLICKABLE);
		click(element);
		browser.findElement(element).sendKeys(text);
	}

	/**
	 * submit element
	 * 
	 * @param By element
	 */
	public void submit(By element) {
		waitUntilElement(element, Expected_Conditions.ELEMENTTOBECLICKABLE);
		browser.findElement(element).submit();
	}

	/**
	 * Continue to the web page (not recommended) on the page This site is not
	 * secure from Internet Explorer
	 * 
	 * _IEOptions.setCapability("acceptInsecureCerts", acceptInsecureCerts) does not
	 * work in Internet Explorer
	 * 
	 */
	public void validateSSL() {
		_validate = _capabilities.getBrowserName();
		if (_validate.equalsIgnoreCase("internet explorer")) {
			_validate = browser.getTitle();
			if (_validate.equalsIgnoreCase("Este site não é seguro")) {
				browser.navigate().to("javascript: document.getElementById ('overridelink').click ()");
			}
		}
	}

	/**
	 * Wait page load
	 */
	public void waitPageLoad() {

		_wait = new WebDriverWait(browser, _timeouts.get("pageLoad") / 1000);

		_wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver wdriver) {
				return ((JavascriptExecutor) browser).executeScript("return document.readyState").equals("complete");
			}
		});
	}

	/**
	 * waits until the element meets the expected condition
	 * 
	 * @param By                  element
	 * 
	 * @param Expected_Conditions expected
	 */
	public void waitUntilElement(By element, Expected_Conditions expected) {
		_wait = new WebDriverWait(browser, _timeouts.get("pageLoad") / 1000);

		try {
			switch (expected) {
			case ALERTISPRESENT:
				_wait.until(ExpectedConditions.alertIsPresent());
				break;
			case ELEMENTSELECTIONSTATETOBE_TRUE:
				_wait.until(ExpectedConditions.elementSelectionStateToBe(element, true));
				break;
			case ELEMENTSELECTIONSTATETOBE_FALSE:
				_wait.until(ExpectedConditions.elementSelectionStateToBe(element, false));
				break;
			case ELEMENTTOBECLICKABLE:
				_wait.until(ExpectedConditions.elementToBeClickable(element));
				break;
			case ELEMENTTOBESELECTED:
				_wait.until(ExpectedConditions.elementToBeSelected(element));
				break;
			case FRAMETOBEAVAILABLEANDSWITCHTOIT:
				_wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(element));
				break;
			case INVISIBILITYOFELEMENTLOCATED:
				_wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
				break;
			case PRESENCEOFELEMENTLOCATED:
				_wait.until(ExpectedConditions.presenceOfElementLocated(element));
				break;
			}
		} catch (Exception e) {
		}
	}

	/**
	 * get web element
	 * 
	 * @param By element
	 * 
	 * @return WebElement
	 */
	public WebElement getWebElement(By element) {
		waitUntilElement(element, Expected_Conditions.PRESENCEOFELEMENTLOCATED);
		return browser.findElement(element);
	}

}
