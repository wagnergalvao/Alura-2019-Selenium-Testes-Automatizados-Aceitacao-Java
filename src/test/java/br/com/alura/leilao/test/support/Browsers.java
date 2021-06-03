package br.com.alura.leilao.test.support;

import java.util.Map;

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

import io.github.bonigarcia.wdm.WebDriverManager;

public enum Browsers {
	
	CHROME {
		@Override
		public String commercialName() {
			return "Google Chrome";
		}

		@Override
		public String capabilityName() {
			return "chrome";
		}

		@Override
		public WebDriver browserInstance(Map<String, Long> timeouts, boolean acceptInsecureCerts) {
			ChromeOptions _chromeOptions = new ChromeOptions();
			_chromeOptions.setAcceptInsecureCerts(acceptInsecureCerts);
			_chromeOptions.setCapability("timeouts", timeouts);
			WebDriverManager.chromedriver().setup();
			return new ChromeDriver(_chromeOptions);
		}
	}, EDGE {
		@Override
		public String commercialName() {
			return "Microsoft Edge";
		}

		@Override
		public String capabilityName() {
			return "msedge";
		}

		@Override
		public WebDriver browserInstance(Map<String, Long> timeouts, boolean acceptInsecureCerts) {
			EdgeOptions _edgeOptions = new EdgeOptions();
			_edgeOptions.setCapability("acceptInsecureCerts", acceptInsecureCerts);
			_edgeOptions.setCapability("timeouts", timeouts);
			WebDriverManager.edgedriver().setup();
			return new EdgeDriver(_edgeOptions);
		}
	}, FIREFOX {
		@Override
		public String commercialName() {
			return "Mozilla Firefox";
		}

		@Override
		public String capabilityName() {
			return "firefox";
		}

		@Override
		public WebDriver browserInstance(Map<String, Long> timeouts, boolean acceptInsecureCerts) {
			FirefoxOptions _fireFoxOptions = new FirefoxOptions();
			_fireFoxOptions.setCapability("acceptInsecureCerts", acceptInsecureCerts);
			_fireFoxOptions.setCapability("timeouts", timeouts);
			WebDriverManager.firefoxdriver().setup();
			return new FirefoxDriver(_fireFoxOptions);
		}
	}, IE {
		@Override
		public String commercialName() {
			return "Internet explorer";
		}

		@Override
		public String capabilityName() {
			return "internet explorer";
		}

		@Override
		public WebDriver browserInstance(Map<String, Long> timeouts, boolean acceptInsecureCerts) {
			InternetExplorerOptions _IEOptions = new InternetExplorerOptions();
			_IEOptions.setCapability("ignoreProtectedModeSettings", true);
			_IEOptions.setCapability("INTODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS", true);
			_IEOptions.setCapability("timeouts", timeouts);
//			_IEOptions.setCapability("acceptInsecureCerts", acceptInsecureCerts);
//			_IEOptions.setAcceptInsecureCerts(acceptInsecureCerts);
			WebDriverManager.iedriver().setup();
			return new InternetExplorerDriver(_IEOptions);
		}
	}, OPERA {
		@Override
		public String commercialName() {
			return "Opera";
		}

		@Override
		public String capabilityName() {
			return "opera";
		}

		@Override
		public WebDriver browserInstance(Map<String, Long> timeouts, boolean acceptInsecureCerts) {
			OperaOptions _operaOptions = new OperaOptions();
			_operaOptions.setCapability("acceptInsecureCerts", acceptInsecureCerts);
			_operaOptions.setCapability("timeouts", timeouts);
			WebDriverManager.operadriver().setup();
			return new OperaDriver(_operaOptions);
		}
	};
	
	public abstract String commercialName();
	public abstract String capabilityName();
	public abstract WebDriver browserInstance(Map<String, Long> timeouts, boolean acceptInsecureCerts);
}
