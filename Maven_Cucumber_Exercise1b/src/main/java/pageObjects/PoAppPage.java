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
	
	//Mozilla works perfectly withou ancestor part, however Chrome and Opera won't work otherwise
	private  static final String xpathOneTab = "//one-app-nav-bar-item-root //span[.='$TabName'] /ancestor::one-app-nav-bar-item-root";
	private static final String tdByInnerText = "//table[@role='grid'] /tbody /tr /td /span //span[.='$InnerText']";
	private static final String thByInnerText = "//table[@role='grid'] /tbody /tr /th /span //a[.='$InnerText']";
	private static final String tdByInnerTextThAndTdNumber = "//table[@role='grid'] /tbody /tr /th /span //a[.='$InnerTex'] /ancestor::tr /td[$ColumnNumber]";
	private static final String tdMenuBtn = "//table[@role='grid'] /tbody /tr /th /span //a[.='$InnerTex'] /ancestor::tr /td[last()]";
	private static final String tdMenuInnerBtn = "//div[@role='menu'] //div[@title='$Action'] /ancestor::a";
	
	@FindBy (xpath = "//button[contains(text(),'Cancel')]")
	private WebElement iFrameCancelBTN;
	
	@FindBy (xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost'] //iframe")
	private WebElement iFrameWindow;
	
	@FindBy (xpath = "//one-app-nav-bar-item-root")
	private List <WebElement> tabOptions;
		 
	 private  static final String xpathBtnNew = "//div[@title='$btnTitle']";
	 
	 /**
	 * Region Constructor
	 */
	public PoAppPage(WebDriver driver) {
		this.driver=driver;
	}
	
	/**
	* Region Getters
	*/
	
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
	public WebElement getOneTab (String tabName) {
		WebElement ret = driver.findElement(By.xpath(xpathOneTab.replace("$TabName", tabName))); //Within path string argument expected by "By.xpath()"  a text replacement is done 
		return ret;
	}
	
	/**
	* <mark>Get</mark> a specific <mark>"new" button</mark> within a tab <mark>by its inner text </mark> which is same as its title property
	* <br><b>You must access a tab before using this method</b>
	* @param innerText
	*/
	public WebElement getNewBtn (String innerText) {
		WebElement btn = driver.findElement(By.xpath(xpathBtnNew.replace("$LabelName", innerText)));
		return btn;
	}
	
	/**
	* Get a specific td from tab page´s table
	* <br><b>You must access a tab before using this method</b>
	* @param innerText
	*/
	public WebElement getTdByInnerText (String innerText) {
		WebElement ret = driver.findElement(By.xpath(tdByInnerText.replace("$InnerText", innerText))); //Within path string argument expected by "By.xpath()"  a text replacement is done 
		return ret;
	}
	
	/**
	* Get a specific th from tab page´s table, this element 
	* <br>helps to click on an account create by example
	* <br><b>You must access a tab before using this method</b>
	* @param innerText
	*/
	public WebElement getThByInnerText (String innerText) {
		WebElement ret = driver.findElement(By.xpath(thByInnerText.replace("$InnerText", innerText))); //Within path string argument expected by "By.xpath()"  a text replacement is done 
		return ret;
	}
	
	/**
	* You'll get td from row that has th "innerText" from column number "columnNumber"
	* <br><b>You must access a tab before using this method</b>
	* <br><mark>columnNumber must start from 3 and on</mark>
	* @param innerText
	* @param columnNumber
	*/
	public WebElement getTdByInnerTextThAndTdNumber (String thInnerText, int tdColumnNum) {
		String path =tdByInnerTextThAndTdNumber;
		path = path.replace("$InnerTex", thInnerText);
		path = path.replace("$ColumnNumber", Integer.toString(tdColumnNum));
		 WebElement ret = driver.findElement(By.xpath(path));
		return ret;
	}
	
	/**
	* Get menu button at the end of each row at level tab
	* <br><b>You must access a tab before using this method</b>
	* @param thText -> tag a hyperlink
	*/
	public WebElement getTdMenuButton (String thText) {
		WebElement ret = driver.findElement(By.xpath(tdMenuBtn.replace("$InnerText", thText))); //Within path string argument expected by "By.xpath()"  a text replacement is done 
		return ret;
	}
	
	/**
	* Get menu button at the end of each row at level tab
	* <br><b>You must run getTdMenuButton method before using current method</b>
	* @param action -> available are: <mark>Delete,Edit,Change Owner</mark>
	*/
	public WebElement getTdMenuInnerBtn (String action) {
		WebElement ret = driver.findElement(By.xpath(tdMenuInnerBtn.replace("$Action", action))); //Within path string argument expected by "By.xpath()"  a text replacement is done 
		return ret;
	}
	
	/**
	* Take into consideration that is suggested to do a brief thread sleep or similar
	* <br>until button is properly loaded
	*/
	public WebElement getiFrameCancelBTN() {
		return iFrameCancelBTN;
	}
	
	/**
	* Take into consideration that is suggested to do a brief thread sleep or similar
	* <br>after getting out from an iframe to avoid duplicity issue due to load charges
	*/
	public WebElement getiFrameWindow() {
		return iFrameCancelBTN;
	}

}
