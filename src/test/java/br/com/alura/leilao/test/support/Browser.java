package br.com.alura.leilao.test.support;

import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * @author Wagner.Galvão
 *
 */
@SuppressWarnings("unchecked")
public class Browser {

	public static WebDriver browser;
	public static Capabilities _capabilities;
	public static Map<String, Long> _timeouts;
	protected WebDriverWait _wait;
	protected String _validate;

	/**
	 * Open the browser
	 * 
	 * @param browserName
	 * @param acceptInsecureCerts:
	 * 			true 	Skip Secure Sockets Layer Validation
	 * 			false	Maintain Secure Sockets Layer validation
	 * 
	 * @return Browser instance
	 */
	public WebDriver open(String browserName, boolean acceptInsecureCerts) {

		Map<String, Long> _newTimeouts = new HashMap<>();
		_newTimeouts.put("implicit", (long) 0);
		_newTimeouts.put("pageLoad", (long) 30000);
		_newTimeouts.put("script", (long) 30000);

		switch (browserName.toLowerCase()) {
		case "internet explorer":
			InternetExplorerOptions _IEOptions = new InternetExplorerOptions();
			_IEOptions.setCapability("ignoreProtectedModeSettings", true);
			_IEOptions.setCapability("INTODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS", true);
			_IEOptions.setCapability("timeouts", _newTimeouts);
//			_IEOptions.setCapability("acceptInsecureCerts", acceptInsecureCerts);
//			_IEOptions.setAcceptInsecureCerts(acceptInsecureCerts);
			WebDriverManager.iedriver().setup();
			browser = new InternetExplorerDriver(_IEOptions);
			break;
		case "chrome":
			ChromeOptions _chromeOptions = new ChromeOptions();
			_chromeOptions.setAcceptInsecureCerts(acceptInsecureCerts);
			_chromeOptions.setCapability("timeouts", _newTimeouts);
			WebDriverManager.chromedriver().setup();
			browser = new ChromeDriver(_chromeOptions);
			break;
		case "msedge":
			EdgeOptions _edgeOptions = new EdgeOptions();
			_edgeOptions.setCapability("acceptInsecureCerts", acceptInsecureCerts);
			_edgeOptions.setCapability("timeouts", _newTimeouts);
			WebDriverManager.edgedriver().setup();
			browser = new EdgeDriver(_edgeOptions);
			break;
		case "firefox":
			FirefoxOptions _fireFoxOptions = new FirefoxOptions();
			_fireFoxOptions.setCapability("acceptInsecureCerts", acceptInsecureCerts);
			_fireFoxOptions.setCapability("timeouts", _newTimeouts);
			WebDriverManager.firefoxdriver().setup();
			browser = new FirefoxDriver(_fireFoxOptions);
			break;
		case "opera":
			OperaOptions _operaOptions = new OperaOptions();
			_operaOptions.setCapability("acceptInsecureCerts", acceptInsecureCerts);
			_operaOptions.setCapability("timeouts", _newTimeouts);
			WebDriverManager.operadriver().setup();
			browser = new OperaDriver(_operaOptions);
			break;
		default:
			assertFalse("Navegador web " + browserName + " não configurado", true);
		}
		browser.manage().window().maximize();
		_capabilities = getCapabilities();
		_timeouts = getTimeouts();
		return browser;
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
	 * @return Browser Name
	 */

	public String getBrowserName() {
		return _capabilities.getBrowserName();
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
	 * Open the URL, wait page load and validates SSL if acceptInsecureCerts true
	 * 
	 * @param url
	 * @param acceptInsecureCerts:
	 * 			true 	Skip Secure Sockets Layer Validation
	 * 			false	Maintain Secure Sockets Layer validation
	 */
	public void navigateTo(String url, boolean acceptInsecureCerts) {
		browser.navigate().to(url);
		waitPageLoad();
		if (acceptInsecureCerts)
			validateSSL();

	}

	/**
	 * Close Browser
	 * @param browser
	 */
	public void quit() {
		if (browser != null) {
			browser.quit();
		}
	}

	/**
	 * Continue to the web page (not recommended) on the page This site is not secure from Internet Explorer
	 * 
	 * _IEOptions.setCapability("acceptInsecureCerts", acceptInsecureCerts) does not work in Internet Explorer
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
	 * @param element             By element
	 * @param expectedConditions: alertIsPresent
	 */
	public void waitUntilElement(By element, EnumExpectedConditions expected) {
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

}
