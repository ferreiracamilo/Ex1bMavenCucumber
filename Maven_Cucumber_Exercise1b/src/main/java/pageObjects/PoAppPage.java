package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PoAppPage {
	
	/**
	* Region Variables
	*/
	public WebDriver driver;
	private  static final String xpathOneApp = "//one-app-nav-bar[contains(@class, 'slds-has-flexi')] /nav /div /one-app-nav-bar-item-root /a /span[.='$TabName']";
	
	@FindBy (xpath = "//one-app-nav-bar[contains(@class, 'slds-has-flexi')] /nav /div /one-app-nav-bar-item-root")
	private List <WebElement> tabOptions;
	
	 @FindBy(xpath="//div[@title='New' or @title='New Report' or @title='New Dashboard']")
	  private WebElement btnNew;
	 
	 /**
	 * Region Constructor
	 */
	public PoAppPage(WebDriver driver) {
		this.driver=driver;
	}
	
	/**
	* Region Getters
	*/
	
	public WebElement getBtnNew () {
		return btnNew;
	}
	
	 /**
		* Get a specific tab within an App by indicating its inner text
		* <br><b>You must click in an app before using this method</b>
		* @param tabName refers to tab innertext
		*/
	public List<WebElement>  getTabOptions () {
		return tabOptions;
	}
	
	/**
	* Get a specific tab within an App by indicating its inner text
	* <br><b>You must click in an app before using this method</b>
	* @param tabName refers to tab innertext
	*/
	public WebElement getOneApp (String tabName) {
		WebElement app = driver.findElement(By.xpath(xpathOneApp.replace("$TabName", tabName))); //Within path string argument expected by "By.xpath()"  a text replacement is done 
		return app;
	}
	
	
	
	
	
	

	

}
