package pageObjects;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PoLoginPage {
	
	public WebDriver driver;
	
	/**
	* Region Variables
	*/
	@FindBy (xpath = "//input[@id='username']")
	private WebElement username;
	
	@FindBy (xpath = "//input[@id='password']")
	private WebElement password;
	
	/**
	* Region Constructor
	*/
	public PoLoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
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
	
	/**
	* Region Methods
	*/
	
	/**
	* Get a string value from a key within Data Properties file
	* @param key -> values admitted are user,password
	*/
	public String getUserCredential (String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\data.properties");
		prop.load(fis);
		
		String ret = prop.getProperty(key);
		return ret;
	}
		
}
