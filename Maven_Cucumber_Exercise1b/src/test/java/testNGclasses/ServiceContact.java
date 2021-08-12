package testNGclasses;

import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import cucumber.api.java.en.Given;
import pageObjects.PoAppPage;
import pageObjects.PoAppPageForm;
import pageObjects.PoLandingPage;
import pageObjects.PoLoginPage;
import resources.Base;

public class ServiceContact extends Base{

	/**
	* Region Variables
	*/	
	String initialTab = "";
	String secondTab = "";
	
	/**
	* Region "Steps" AKA TestNG tags
	*/
	
	@BeforeClass
	public void initialize_the_browser_and_login() throws Throwable {
		//Initilize driver and access url
		driver = initializeDriver("chrome");
		driver.get("https://login.salesforce.com");
		
		//Complete login process
		PoLoginPage poLP = new PoLoginPage(driver);
    	String user = poLP.getUserCredential("user");
    	String password = poLP.getUserCredential("password");
    	
    	poLP.getUserID().sendKeys(user);
    	poLP.getPassword().sendKeys(password);
    	poLP.getPassword().submit();
    	clickOnWaffleAndAccessContacts();
	}
	
	@Test
	public void tcPositiveNewTabCreateContact(){
		eraseAllAccountRecords();
		clickContactCreatingNewTab();
		accessNewTabAndSaveTabs();
		clickOnNewButton();
		inputInfoToContactForm("1stAccount");
		clickOnSaveButton();
		handleSuccessNotification();
		accessInitialTab();
	}
	
	@AfterClass
	public void close_browser() throws Throwable {
		driver.quit();
	}
	
    
    /**
	* Aux Methods
	*/
	
	public void accessInitialTab() {
		System.out.println("Method - user_goes_back_to_initial_tab - initial tab is "+ initialTab);
		System.out.println("Method - user_goes_back_to_initial_tab - second tab is " + secondTab);
		driver.switchTo().window(initialTab);
	}
	
	public void inputInfoToContactForm(String accName) {
	    //Exercise 1b - 4 part
		PoAppPageForm contactForm = new PoAppPageForm(driver);
		contactForm.waitUntilFormLoaded();
		
		//Mailing street
		contactForm.getFormElementLvl1("Mailing Street", "textArea").sendKeys("18 de julio");;
		
		//1st Name field
		contactForm.getFormElementLvl1("First Name", "textField").sendKeys("Rodolfo");
		
		//Salutation field
		contactForm.getFormElementLvl1("Salutation", "comboBox").click();
		contactForm.getFormComboItemLvl2("Salutation", "Mr.").click();
				
		//Last Name field
		contactForm.getFormElementLvl1("*Last Name", "textField").sendKeys("Caceres");
		
		
		WebElement accNameSearchBox = contactForm.getFormElementLvl1("Account Name", "searchBox ");
		contactForm.waitElement(accNameSearchBox);
		contactForm.moveNclick(accNameSearchBox);
		//accNameSearchBox.click();
		
		WebElement accountToSelect = contactForm.getSuggestedTextOptionLvl2("Account Name", accName);
		accountToSelect.click();
	}
	
	public void handleSuccessNotification(){
	    //verificar hay success notification y cerrarla
		PoAppPage tab = new PoAppPage(driver);
		WebElement successDialogBTN = tab.getcloseConfirmSucessBTN();
		tab.waitElement(successDialogBTN);
		Assert.assertEquals(successDialogBTN.isDisplayed(), true);
		successDialogBTN.click();
	}
	
	public void clickOnSaveButton(){
		PoAppPageForm form = new PoAppPageForm(driver);
	    form.getFormBtnSave().click();
	}
	
	public void clickOnNewButton(){
		PoAppPage pageTab = new PoAppPage(driver);
		pageTab.waitUntilURLTabLoaded(); //temporary
		
		WebElement newBtn = pageTab.getNewBtn("New");
		pageTab.waitElement(newBtn);
		newBtn.click();
		PoAppPageForm form = new PoAppPageForm(driver);
		form.waitUntilFormLoaded(); //verify form loaded successfully
	}
	
	public void accessNewTabAndSaveTabs() {
		initialTab  = driver.getWindowHandle(); //Get Current Window Tab
		ArrayList tabs = new ArrayList (driver.getWindowHandles()); 
		secondTab = (String) tabs.get(1); //Get Second Window Tab (new)
		
		System.out.println("Method - user_goes_to_new_tab - initial tab is "+ initialTab);
		System.out.println("Method - user_goes_to_new_tab - second tab is " + secondTab);
		driver.switchTo().window(secondTab);
	}
	
	public void clickContactCreatingNewTab() {
	    PoAppPage tab = new PoAppPage(driver);
	    WebElement contactsTab = tab.getOneTab("Contacts");
	    tab.clickCTRLT(contactsTab);
	}
	
	public void eraseAllAccountRecords(){
		//Note if the account is related to other element as Contact record, the contact record will be erased
		PoAppPage pageTabRecords = new PoAppPage(driver);
		int qtyAccountRecords = pageTabRecords.getThList().size();
		if(qtyAccountRecords>0) {
			for (int i = qtyAccountRecords; i > 0; i--) {
				WebElement btnMenuRow = pageTabRecords.getTdMenuBtnByIndex(i); 
				btnMenuRow.click();
				WebElement deleteActionBTN = pageTabRecords.getTdMenuInnerBtn("Delete"); //There will be a unique visible delete button after clicking on menu button at row level
				pageTabRecords.waitElement(deleteActionBTN);
				deleteActionBTN.click();
				WebElement deleteConfirmBTN = pageTabRecords.getConfirmDeleteBTN(); //After asking to delete system will prompt confirmation request
				pageTabRecords.waitElement(deleteConfirmBTN);
				deleteConfirmBTN.click();
				WebElement deleteDialogBTN = pageTabRecords.getcloseConfirmSucessBTN(); //After confirming delete request will appear a confirmation success
				pageTabRecords.waitElement(deleteDialogBTN);
				deleteDialogBTN.click();
			}
		}
	}
	
	public void clickOnWaffleAndAccessContacts(){
		//LANDING PAGE
		PoLandingPage poLP = new PoLandingPage(driver);
    	poLP.getWaffleMenuButton().click(); //click on waffle menu button
    	poLP.getWaffleApp("Service").click(); //clicks on service app button from waffle menu
    	
    	//APP PAGE
    	PoAppPage poAP = new PoAppPage(driver); 
    	poAP.waitUntilURLAppLoaded(); //Verify Service app has been loaded
    	//>ACCESS ACCOUNTS TAB 
	    poAP.getOneTab("Contacts").click();
	    poAP.waitUntilURLTabLoaded();
	}
	
}
