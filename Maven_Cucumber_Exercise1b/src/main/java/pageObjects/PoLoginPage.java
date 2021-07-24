package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PoLoginPage {
	
	public WebDriver driver;
	
	/**
	* Region Variables
	*/
	@FindBy (xpath = ".//*[@id='username']")
	private WebElement username;
	
	@FindBy (xpath = ".//*[@id='password']")
	private WebElement password;
	
	/**
	* Region Constructor
	*/
	public PoLoginPage(WebDriver driver) {
		this.driver=driver;
	}
	
	/**
	* Region Getters
	*/
	public WebElement getUserID () {
		return username;
	}
	
	public WebElement getPassword () {
		return password;
	}

}
