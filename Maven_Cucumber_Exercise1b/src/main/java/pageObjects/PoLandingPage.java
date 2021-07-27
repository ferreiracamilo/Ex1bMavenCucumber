package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PoLandingPage {
	
	/**
	* Region Variables
	*/
	
	public WebDriver driver;
	
	@FindBy (xpath = "//button[contains(@class, 'slds-button slds-icon-waffle_')]")
	private WebElement waffleMenuButton;
	
	@FindBy (xpath = "//div[contains(@id, 'al-menu-dropdown-apps-id-')] //one-app-launcher-menu-item")
	private List <WebElement> waffleOptions;
	
	private  static final String xpathWaffleApp = "//div[contains(@id, 'al-menu-dropdown-apps-id-')] //one-app-launcher-menu-item[.='$AppName']";
	
	/**
	* Region Constructor
	*/
	public PoLandingPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	/**
	* Region Getters
	*/
	public WebElement getWaffleMenuButton () {
		return waffleMenuButton;
	}
	
	public List<WebElement>  getWaffleOptions () {
		return waffleOptions;
	}
	
	public WebElement getWaffleApp (String appName) {
		WebElement ret = driver.findElement(By.xpath(xpathWaffleApp.replace("$AppName", appName))); //Within path string argument expected by "By.xpath()"  a text replacement is done 
		return ret;
	}
	
}



