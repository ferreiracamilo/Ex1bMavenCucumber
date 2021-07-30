package pageObjects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Developed to manage App and its respective tabs
 * */
public class PoAppPage {
	
	/**
	* Region Variables
	*/
	public WebDriver driver;
	public WebDriverWait wait; //Temporary to be decide if to be implemented permanently or not
	
	private  static final String xpathBtnNew = "//ul[contains(@class, 'branding-actions slds-button-group')] //li[@class='slds-button slds-button--neutral'] //a[@title='$btnTitle']";  
	private  static final String xpathOneTab = "//one-app-nav-bar-item-root[@data-assistive-id='operationId'] //a[contains(@href,'/lightning/') and @tabindex='0'] //span[.='$TabName'] //ancestor::one-app-nav-bar-item-root";
	private static final String thByInnerTextList = "//table[@role='grid'] /tbody /tr /th /span //a[.='$InnerText']";
	private static final String thByInnerText = "(//table[@role='grid'] /tbody /tr /th /span //a[.='$InnerText'])[$Index]";
	private static final String tdUniqueThNColumnIndex = "(//table[@role='grid'] /tbody /tr /th /span //a[.='$InnerText'] /ancestor::tr /td[$ColumnNumber])[$Index]";
	private static final String tdMenuBtnBythInnerText = "(//table[@role='grid'] /tbody /tr /th /span //a[.='$InnerText'] /ancestor::tr /td[last()])[$Index]";
	private static final String tdMenuBtnByIndex = "(//table[@role='grid'] //tbody //tr //th //span //a /ancestor::tr /td[last()])[$Index]";
	private static final String tdMenuInnerBtn = "//div[contains(@class,'visible')] //div[@role='menu'] //div[@title='$Action'] /ancestor::a";
	private static final String tabNotLoadedURL = "https://$NameDomain-dev-ed.lightning.force.com/lightning/o/$NameTab/home";
	private static final String appNotLoadedURL = "https://$NameDomain-dev-ed.lightning.force.com/one.app";
	
	/* EXPERIMENTAL without getter temporarily */
	//Path to be implemented in future improvement, gives the possibility to get inner text of current selected tab. Tab must be loaded completely previosly
	@FindBy (xpath = "//one-app-nav-bar-item-root[contains(@class,'slds-is-active')] //span")
	private WebElement tabSelected;

	//Looks for a specific tab by its inner text and being selected
	private static final String tabLabelSelected = "//one-app-nav-bar-item-root[contains(@class,'slds-is-active')] //span[.='$TabName']"; 
	/* EXPERIMENTAL */
	
	@FindBy (xpath = "//table[@role='grid'] /tbody /tr /th /span //a")
	private List<WebElement> thList;
	
	//If you request to delete a record account pop up will show displaying this button
	@FindBy (xpath = "//div[@class='modal-footer slds-modal__footer'] //button[.='Delete']")
	private WebElement confirmDeleteBTN;
	
	//After editing, creating, deleting (after clicking on confirmDeleteBTN) is needed to close success confirmation dialog
	@FindBy (xpath = "//div[contains(@class, 'slds-theme--success slds-notify')] //button[contains(@class,'slds-button slds-button_icon toastClose')]")
	private WebElement closeConfirmSucessBTN;
	
	@FindBy (xpath = "//button[contains(text(),'Cancel')]")
	private WebElement iFrameCancelBTN;
	
	@FindBy (xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost'] //iframe")
	private WebElement iFrameWindow;
	
	//Had to change this xpath from shorten way to this one, otherwise would not work
	//one-app-nav-bar[contains(@class, 'slds-has-flexi')] /nav /div /one-app-nav-bar-item-root
	//div[@role='list'] //one-app-nav-bar-item-root
	@FindBy (xpath = "//one-app-nav-bar-item-root[@data-assistive-id='operationId'] //a[contains(@href,'/lightning/') and @tabindex='0'] //span")
	private List <WebElement> tabOptions;
	
	 /**
	 * Region Constructor
	 */
	public PoAppPage(WebDriver driver) {
		this.driver=driver;
		this.wait =new WebDriverWait(this.driver, 80);
		PageFactory.initElements(driver, this);
	}
	
	/**
	* Region Getters
	*/
	
	 /**
	* Get delete confirmation button after requesting to delete an account record, contact record, etc
	* <br><b>You must request to delete a record before using this method</b>
	*/
	public WebElement getConfirmDeleteBTN   () {
		return confirmDeleteBTN;
	}
	
	/**
	* After editing, creating, deleting  is needed to close success confirmation dialog
	* DELETE CASE: Bare in mind dialog won't appear until suppression is confirmed with button obtained from method getConfirmDeleteBTN()
	* <br><b>You must complete a record manipulation successfully to use this method</b>
	*/
	public WebElement getcloseConfirmSucessBTN () {
		return closeConfirmSucessBTN;
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
		return iFrameWindow;
	}
	
	 /**
		* Get all tabs within in an app
		* <br><b>You must click in an app before using this method</b>
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
		WebElement btn = driver.findElement(By.xpath(xpathBtnNew.replace("$btnTitle", innerText)));
		return btn;
	}
	
	/**
	* This method will retrieve all Th tags present within tab's table
	* <br>Take into consideration for example it's possible to create two account records under same AccountName
	* <br><b>You must access a tab before using this method and should have some record available already</b>
	*/
	public List<WebElement> getThList (){
		return thList;
	}
	
	/**
	* This method work to know how many elements are matching under the same th innertext.
	* <br>Take into consideration for example it's possible to create two account records under same AccountName
	* <br><b>You must access a tab before using this method and should have some record available already</b>
	* @param thInnerText Specifiy th innerhead (th contains tag <a> hyperlink)
	*/
	public List<WebElement> getThByInnerTextList (String thInnerText){
		List <WebElement> ret = null;
		String path = thByInnerTextList.replace("$InnerText'",thInnerText);
		ret = driver.findElements(By.xpath(path));
		return ret;
	}
	
	/**
	* Get a unique th element even if there's any other row with same th's innertext
	* <br>Take into consideration for example it's possible to create two account records under same AccountName
	* <br><b>You must access a tab before using this method and should have some record available already</b>
	* @param thInnerText Specifiy th innerhead (th contains tag <a> hyperlink)
	* @param index  -> Starts by 1 (due to possible th with same innertext, if not index should be 1)
	*/
	public WebElement getThByInnerText (String thInnerText, int index) {
		WebElement ret = null;
		String path = thByInnerText;
		path = path.replace("$InnerText", thInnerText);
		path = path.replace("$Index", Integer.toString(index));
		return ret;
	}
	
	/**
	* Get a unique th element even if there's any other row with same th's innertext
	* <br>Take into consideration for example it's possible to create two account records under same AccountName
	* <br><b>You must access a tab before using this method and should have some record available already</b>
	* @param thInnerText -> Specifiy th innerhead (th contains tag <a> hyperlink)
	* @param column -> Indicate column number to retrieve
	* @param index  -> Starts by 1 (due to possible th with same innertext, if not index should be 1)
	*/
	public WebElement getTdUniqueThNColumnIndex (String thInnerText, int column, int index) {
		WebElement ret = null;
		String path = tdUniqueThNColumnIndex;
		path = path.replace("$InnerText", thInnerText);
		path = path.replace("$ColumnNumber", Integer.toString(column));
		path = path.replace("$Index", Integer.toString(index));
		ret = driver.findElement(By.xpath(path));
		return ret;
	}
	
	/**
	* Get a unique menu button (arrow) under row of specific th
	* <br>Take into consideration for example it's possible to create two account records under same AccountName
	* <br><b>You must access a tab before using this method and should have some record available already</b>
	* @param thInnerText -> Specifiy th innerhead (th contains tag <a> hyperlink)
	* @param index  -> Starts by 1 (due to possible th with same innertext, if not index should be 1)
	*/
	public WebElement getTdMenuBtnBythInnerText (String thInnerText, int index) {
		WebElement ret = null;
		String path = tdMenuBtnBythInnerText;
		path = path.replace("$InnerText", thInnerText);
		path = path.replace("$Index", Integer.toString(index));
		ret = driver.findElement(By.xpath(path));
		return ret;
	}
	
	/**
	* Get a unique menu button under row by order (index) in html doc
	* <br><b>You must access a tab before using this method and should have some record available already</b>
	* @param index  -> Starts by 1
	*/
	public WebElement getTdMenuBtnByIndex (int index) {
		WebElement ret = null;
		String path = tdMenuBtnByIndex.replace("$Index", Integer.toString(index));
		ret = driver.findElement(By.xpath(path));
		return ret;
		// private static final String tdMenuBtnByIndex = "(//table[@role='grid'] //tbody //tr //th //span //a /ancestor::tr /td[last()])[$Index]";
	}
	
	/**
	* Get an action button from row menu button, will get only visible one
	* <br><b>You must access a tab before using this method and should have some record available already</b>
	* @param action -> values available are: <mark>Edit,Delete, Change Owner</mark>
	*/
	public WebElement getTdMenuInnerBtn (String action) {
		WebElement ret = null;
		String path = tdMenuInnerBtn.replace("$Action", action);
		ret = driver.findElement(By.xpath(path));
		return ret;
	}
	
	/**
	* Region Methods
	*/
	
	/**
	* Verify tab within app url has loaded successfully
	* As reference current Home Page URL is -> https://none787-dev-ed.lightning.force.com/lightning/setup/SetupOneHome/home
	* Every time you click on an app you'll get the link containing "-dev-ed.lightning.force.com/lightning/page/chatter" 
	*/
	public void waitUntilURLTabLoaded () {
		//this.wait.until(ExpectedConditions.urlContains("?"));
		this.wait.until(ExpectedConditions.or(ExpectedConditions.urlContains("?"),ExpectedConditions.urlContains("dev-ed.lightning.force.com/lightning/page/home"),ExpectedConditions.urlContains("-dev-ed.lightning.force.com/lightning/page/chatter")));
	}
	
	/**
	* Before hand having the knowledge that only Home and Chatter tabs won't have '?' after are loaded successfully
	* <br>Method reviews URL has '?' or the explicits links of two previous tabs mentioned
	*/
	public void waitUntilURLAppLoaded () {
		this.wait.until(ExpectedConditions.urlContains("-dev-ed.lightning.force.com/lightning/page/home"));
	}
	
	public void jsClick (WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("arguments[0].click();", element);
	}
	
	public void clickCTRLT (WebElement ele) {
		Actions action = new Actions (driver);
		action.keyDown(Keys.CONTROL).build().perform();
		ele.click();
		
		// Alternative -> ele.sendKeys(Keys.chord(Keys.CONTROL,Keys.ENTER));
	}
	
	public void waitElement (WebElement ele) {
		this.wait.until(ExpectedConditions.visibilityOf(ele));
	}

}
