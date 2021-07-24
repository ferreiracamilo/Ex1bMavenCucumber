package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PoLandingPage {
	
	/**
	* Region Variables
	*/
	public WebDriver driver;
	private  static final String xpathOneApp = "//div[contains(@id, 'al-menu-dropdown-apps-id-')] //one-app-launcher-menu-item[.='$AppName']";
	
	@FindBy (xpath = "//button[contains(@class, 'slds-button slds-icon-waffle_')]")
	private WebElement waffleButton;
	
	@FindBy (xpath = "//div[contains(@id, 'al-menu-dropdown-apps-id-')] //one-app-launcher-menu-item")
	private List <WebElement> waffleOptions;
	
	/**
	* Region Constructor
	*/
	public PoLandingPage(WebDriver driver) {
		this.driver=driver;
	}
	
	/**
	* Region Getters
	*/
	public WebElement getWaffleButton () {
		return waffleButton;
	}
	
	public List<WebElement>  getWaffleOptions () {
		return waffleOptions;
	}
	
	public WebElement getOneApp (String appName) {
		WebElement app = driver.findElement(By.xpath(xpathOneApp.replace("$AppName", appName))); //Within path string argument expected by "By.xpath()"  a text replacement is done 
		return app;
	}
	
}



