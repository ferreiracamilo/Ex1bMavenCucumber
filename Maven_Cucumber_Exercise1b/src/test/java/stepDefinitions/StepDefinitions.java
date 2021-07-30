package stepDefinitions;

import java.awt.List;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import cucumber.api.java.en.*;
import pageObjects.PoAppPage;
import pageObjects.PoAppPageForm;
import pageObjects.PoLandingPage;
import pageObjects.PoLoginPage;
import resources.DriverFactory;

public class StepDefinitions extends DriverFactory{
	
	/**
	* Region Variables
	*/
	
	String initialTab = "";
	String secondTab = "";

	/* 
	Instanciar en clase
	Set<String> windows = null; // [parentid,childid,subchildId]
	Iterator<String> it = null;
	String parentId = null;
	String childId = null;
	
	Cargar value en metoodo
	windows = driver.getWindowHandles(); // [parentid,childid,subchildId]
	it = windows.iterator();
	parentId = it.next();
	childId = it.next(); 
	*/
    
	/**
	* Region Steps
	*/
	
	@Given("^Initialize the browser with \"([^\"]*)\"$")
	public void initialize_the_browser_with(String arg1) throws Throwable {
		driver = initializeDriver(arg1);
	}

	@Given("^User navigates to \"([^\"]*)\" Site$")
	public void user_navigates_to_Site(String arg1) throws Throwable {
		driver.get(arg1);
	}

	@Given("^User enters credentials and logs in$")
	public void user_enters_credentials_and_logs_in() throws Throwable {
		PoLoginPage poLP = new PoLoginPage(driver);
    	String user = poLP.getUserCredential("user");
    	String password = poLP.getUserCredential("password");
    	
    	poLP.getUserID().sendKeys(user);
    	poLP.getPassword().sendKeys(password);
    	poLP.getPassword().submit();
	}

	@Given("^User clicks on waffle menu button$")
	public void user_clicks_on_waffle_menu_button() throws Throwable {
		PoLandingPage poLP = new PoLandingPage(driver);
    	poLP.getWaffleMenuButton().click();
	}

	@Given("^User clicks on Service button$")
	public void user_clicks_on_Service_button() throws Throwable {
		PoLandingPage poLP = new PoLandingPage(driver);
    	poLP.getWaffleApp("Service").click();
    	
    	/* Verifying Service has been loaded succesfully*/
    	PoAppPage poAP = new PoAppPage(driver);
    	poAP.waitUntilURLAppLoaded();
	}
	
	@Given("^User clicks on Accounts tab$")
	public void user_clicks_on_Accounts_tab() throws Throwable {
	    PoAppPage poAP = new PoAppPage(driver);
	    poAP.getOneTab("Accounts").click();
	    poAP.waitUntilURLTabLoaded();
	}
	
	@Given("^User clicks on Contacts tab$")
	public void user_clicks_on_Contacts_tab() throws Throwable {
	    PoAppPage poAP = new PoAppPage(driver);
	    poAP.getOneTab("Contacts").click();
	    poAP.waitUntilURLTabLoaded();
	}
	
	@Given("^User clicks every tab \\(click new and cancel if applies\\)$")
	public void user_clicks_every_tab_click_new_and_cancel_if_applies() throws Throwable {
		PoAppPage poAP = new PoAppPage(driver);
		PoAppPageForm poAPF = new PoAppPageForm(driver);
		
    	//IMPORTANT NOTE: There are some explicit waits commented since it's being 
		for (int i=1 ; i< poAP.getTabOptions().size(); i++ ) {
    		WebElement tab = poAP.getTabOptions().get(i); 
    		poAP.jsClick(tab);
    		poAP.waitUntilURLTabLoaded();
    		
    		if(tab.getText().equalsIgnoreCase("Accounts") || tab.getText().equalsIgnoreCase("Contacts") || tab.getText().equalsIgnoreCase("Cases")) {
    			WebElement newBTN = poAP.getNewBtn("New");
				poAP.waitElement(newBTN);
				newBTN.click();
				poAPF.getFormBtnCancel().click();
    		}else if (tab.getText().equalsIgnoreCase("Reports")) {
    			WebElement reportNew = poAP.getNewBtn("New Report");
				//poAP.waitElement(reportNew);
				reportNew.click();
				//driver.switchTo().frame(poAP.getiFrameWindow2("New Report"));
				driver.switchTo().frame(poAP.getiFrameWindow());
				//poAP.waitElement(poAP.getiFrameCancelBTN());
				poAP.getiFrameCancelBTN().click();
				driver.switchTo().defaultContent();
    		}else if(tab.getText().equalsIgnoreCase("Dashboards")){
    			WebElement dashboardNew = poAP.getNewBtn("New Dashboard");
    			//poAP.waitElement(dashboardNew);
				dashboardNew.click();
				//driver.switchTo().frame(poAP.getiFrameWindow2("New Dashboard"));
				driver.switchTo().frame(poAP.getiFrameWindow());
				poAP.waitElement(poAP.getiFrameCancelBTN());
				poAP.getiFrameCancelBTN().click();
				driver.switchTo().defaultContent();
    		} //end ifs
    	} //end for 
	}

	@Given("^User erase all respective records$")
	public void user_erase_all_respective_records() throws Throwable {
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
	
	@Given("^User clicks on New button$")
	public void user_clicks_on_New_button() throws Throwable {
		PoAppPage pageTab = new PoAppPage(driver);
		pageTab.waitUntilURLTabLoaded(); //temporary
		
		WebElement newBtn = pageTab.getNewBtn("New");
		pageTab.waitElement(newBtn);
		newBtn.click();
		PoAppPageForm form = new PoAppPageForm(driver);
		form.waitUntilFormLoaded(); //verify form loaded successfully
	}

	@Given("^User inputs \"([^\"]*)\" as AccountName and fill rest of the account form$")
	public void user_inputs_as_AccountName_and_fill_rest_of_the_form(String arg1) throws Throwable {
	    //Creating a new account
		PoAppPageForm formAccount = new PoAppPageForm(driver);   
		/**
		* Build path, get WebElement and input information textFields
		*/
	    Hashtable txtInputValues = formAccount.valuesTxtFields();
	    Set<String> keysTxtInput = txtInputValues.keySet(); //get all keys from hashtable
        for(String key: keysTxtInput){
        	String labelName = key;
        	WebElement txtFieldTarget = formAccount.getFormElementLvl1(labelName, "textField"); //Build WebElement with respective path
        	String txtFieldValue = "";
        	if(labelName != "*Account Name") {
            	txtFieldValue = (String) txtInputValues.get(labelName); //Get value for that element
        	}else {
        		txtFieldValue = arg1;
        	}
        	txtFieldTarget.sendKeys(txtFieldValue);
        }
        
		/**
		* Build path, get WebElement and input information textAreas
		*/
        Hashtable txtAreaValues = formAccount.valuesTxtAreas();
	    Set<String> keysTxtArea = txtAreaValues.keySet(); //get all keys from hashtable
        for(String key: keysTxtArea){
        	String labelName = key;
            WebElement txtAreaTarget =formAccount.getFormElementLvl1(labelName, "textArea"); //Build WebElement with respective path
        	String txtAreaValue = (String) txtAreaValues.get(labelName); //Get value for that element
        	txtAreaTarget.sendKeys(txtAreaValue);
        }
        
		/**
		* Build path, get WebElement and select option for comboBoxes
		*/
        Hashtable comboBoxValues = formAccount.valuesComboBox();
        Set<String> keysComboBox= comboBoxValues.keySet(); //get all keys from hashtable
        for(String key: keysComboBox){
        	String labelName = key;
            
        	//Get comboBox element and click it
        	WebElement comboBoxTarget =formAccount.getFormElementLvl1(labelName, "comboBox");
        	formAccount.moveNclick(comboBoxTarget);
            
        	//Get comboBox option to click and click it 
            String comboBoxValue = (String) comboBoxValues.get(labelName);
            WebElement comboOptionToClick = formAccount.getFormComboItemLvl2(labelName, comboBoxValue);
            formAccount.waitElement(comboOptionToClick);
            comboOptionToClick.click();
        }
        
        /**
		* Work with calendar item
		*/
        WebElement clickCalendar = formAccount.getFormElementLvl1("SLA Expiration Date", "calendar");
        formAccount.moveNclick(clickCalendar);
        WebElement itemCalendar = formAccount.getFormCalendarItemsLvl2("SLA Expiration Date").get(1); //It retrieves first element from calendar items available
        itemCalendar.click();
	}

	@Given("^User Clicks on Save button$")
	public void user_Clicks_on_Save_button() throws Throwable {
		PoAppPageForm form = new PoAppPageForm(driver);
	    form.getFormBtnSave().click();
	}

	@Then("^Close browser$")
	public void close_browser() throws Throwable {
		driver.close();
		System.out.println("cierro browser");
	}

	@When("^System launchs error stop sign icon due to not filling mandatory fields$")
	public void system_launchs_error_stop_sign_icon_due_to_filling_mandatory_fields() throws Throwable {
		PoAppPageForm form = new PoAppPageForm(driver);
		WebElement errorMandatoryField = form.getMandatoryNotFillError();
		Assert.assertEquals(errorMandatoryField.isDisplayed(), true);
	}
	
	@When("^warning message \"([^\"]*)\" is displayed$")
	public void warning_message_is_displayed(String arg1) throws Throwable {
		PoAppPageForm form = new PoAppPageForm(driver);
		WebElement errorEmployeesField = form.getNumericEmpError();
		Assert.assertEquals(errorEmployeesField.isDisplayed(), true);
	}

	@Given("^User clicks on Contacts creating a new tab$")
	public void user_clicks_on_Contacts_creating_a_new_tab() throws Throwable {
	    PoAppPage tab = new PoAppPage(driver);
	    WebElement contactsTab = tab.getOneTab("Contacts");
	    tab.clickCTRLT(contactsTab);
	}

	//CHECK NOTATION
	@Given("^User goes to new tab$")
	public void user_goes_to_new_tab() throws Throwable {
		
		System.out.println("ejecuto goes to new tab");
		
		initialTab  = driver.getWindowHandle(); //Get Current Window Tab
		ArrayList tabs = new ArrayList (driver.getWindowHandles()); 
		secondTab = (String) tabs.get(1); //Get Second Window Tab (new)
		
		System.out.println("Method - user_goes_to_new_ta - initial tab is "+ initialTab);
		System.out.println("Method - user_goes_to_new_ta - second tab is " + secondTab);
		driver.switchTo().window(secondTab);
	}

	//CHECK NOTATION
	@And("^User goes back to initial tab$")
	public void user_goes_back_to_initial_tab() throws Throwable {
		System.out.println("Method - user_goes_back_to_initial_tab - initial tab is "+ initialTab);
		System.out.println("Method - user_goes_back_to_initial_tab - second tab is " + secondTab);
		driver.switchTo().window(initialTab);
		
	}

	@Given("^User click menu \\(arrow down\\) and click Edit button from row which contains \"([^\"]*)\"$")
	public void user_click_menu_arrow_down_and_click_Edit_button_from_row_which_contains(String arg1) throws Throwable {
	    PoAppPage tabAccount= new PoAppPage(driver);
	    
	    //Since it's being called again, is needed to check if tab was loaded succesfully for 2nd run
	    tabAccount.waitUntilURLTabLoaded();
	    
	    //Get, wait and click rowMenu button
	    WebElement rowMenuBtn = tabAccount.getTdMenuBtnBythInnerText(arg1, 1);
	    tabAccount.waitElement(rowMenuBtn);
	    rowMenuBtn.click();
	    
	    //Get, wait and click menu edit button
	    WebElement edit = tabAccount.getTdMenuInnerBtn("Edit");
	    tabAccount.waitElement(edit);
	    edit.click();
	    
	    /* Wait until form is loaded after clicking edit */
	    PoAppPageForm form = new PoAppPageForm(driver);
	    form.waitUntilFormLoaded();
	    
	}

	@Given("^User modify Rating to \"([^\"]*)\", Upsell Opportunity to \"([^\"]*)\" and Type to \"([^\"]*)\" dropdown options$")
	public void user_modify_Rating_to_Upsell_Opportunity_to_and_Type_to_dropdown_options(String arg1, String arg2, String arg3) throws Throwable {
		PoAppPageForm formEditAccount = new PoAppPageForm(driver);
		//Modifying comboRating
		WebElement comboRating = formEditAccount.getFormElementLvl1("Rating", "comboBox");
		formEditAccount.moveNclick(comboRating);
		WebElement optionRating = formEditAccount.getFormComboItemLvl2("Rating", arg1);
		optionRating.click();
		
		//Modifying comboUpsell
		WebElement comboUpsell = formEditAccount.getFormElementLvl1("Upsell Opportunity", "comboBox");
		formEditAccount.moveNclick(comboUpsell);
		WebElement optionUpsell = formEditAccount.getFormComboItemLvl2("Upsell Opportunity", arg2);
		optionUpsell.click();
		
		//Modifying comboType
		WebElement comboType = formEditAccount.getFormElementLvl1("Type", "comboBox");
		formEditAccount.moveNclick(comboType);
		WebElement optionType = formEditAccount.getFormComboItemLvl2("Type", arg3);
		optionType.click();
	}
	
	//User modify Rating to "Cold", Upsell Opportunity to "Yes" and Type to "Other" dropdown options
	//User verifies dropdown values Rating is "Cold", Upsell Opportunity is "Yes" and Type is "Other" on "1stAccount" 
	@When("^User verifies dropdown values: Rating is \"([^\"]*)\", Upsell Opportunity is \"([^\"]*)\" and Type is \"([^\"]*)\" on \"([^\"]*)\"$")
    public void user_verifies_dropdown_values_rating_is_something_upsell_opportunity_is_something_and_type_is_something_on_something(String strArg1, String strArg2, String strArg3, String strArg4) throws Throwable {
		
		PoAppPageForm formEditAccount = new PoAppPageForm(driver);
		
		//Verifying comboRating
		WebElement comboRating = formEditAccount.getFormElementLvl1("Rating", "comboBox");
		formEditAccount.moveNclick(comboRating);
		String valueRating = formEditAccount.getFormComboItemSelectedLvl2("Rating").getText();
		Assert.assertEquals(valueRating , strArg1);
		
		//Modifying comboUpsell
		WebElement comboUpsell = formEditAccount.getFormElementLvl1("Upsell Opportunity", "comboBox");
		formEditAccount.moveNclick(comboUpsell);
		String valueUpsell = formEditAccount.getFormComboItemSelectedLvl2("Upsell Opportunity").getText();
		Assert.assertEquals(valueUpsell , strArg2);
		
		//Modifying comboType
		WebElement comboType = formEditAccount.getFormElementLvl1("Type", "comboBox");
		formEditAccount.moveNclick(comboType);
		String valueType = formEditAccount.getFormComboItemSelectedLvl2("Type").getText();
		Assert.assertEquals(valueType , strArg3);
    }
	
	@When("^System launchs sucess notification$")
	public void system_launchs_sucess_notification() throws Throwable {
	    //verificar hay success notification y cerrarla
		PoAppPage tab = new PoAppPage(driver);
		WebElement successDialogBTN = tab.getcloseConfirmSucessBTN();
		tab.waitElement(successDialogBTN);
		Assert.assertEquals(successDialogBTN.isDisplayed(), true);
		successDialogBTN.click();
	}

	@Given("^User modify Employees input field to \"([^\"]*)\"$")
	public void user_modify_Employees_input_field_to(String arg1) throws Throwable {
	    PoAppPageForm accountForm =  new PoAppPageForm (driver);
	    String value = arg1;
	    System.out.println("Value iniciando el step"+value);
	    WebElement employeesField = accountForm.getFormElementLvl1("Employees", "textField");
	    accountForm.eraseTextField(employeesField);
	    accountForm.getFormElementLvl1("Employees", "textField").sendKeys(value);
	    System.out.println("Value finalizando el step"+value);
	}
	
	@Given("^User select within Contact form account name field as previous account record -> \"([^\"]*)\"$")
	public void user_select_as_Account_name_the_record_previously_created_as(String arg1) throws Throwable {
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
		
		WebElement accountToSelect = contactForm.getSuggestedTextOptionLvl2("Account Name", arg1);
		accountToSelect.click();
			
	}
	
	@And("^User inputs (.+) into Account Name input, (.+) into Phone input, (.+) into Website input and (.+) into Tycker Symbol input$")
	public void user_inputs_into_account_name_input_into_phone_input_into_website_input_and_into_tycker_symbol_input(
			String accountname, String phonenumber, String websitename, String tyckersymbol) throws Throwable {
			PoAppPageForm accountForm = new PoAppPageForm (driver);
			
			accountForm.getFormElementLvl1("*Account Name", "textField").sendKeys(accountname);
			accountForm.getFormElementLvl1("Phone", "textField").sendKeys(phonenumber);
			accountForm.getFormElementLvl1("Website", "textField").sendKeys(websitename);
			accountForm.getFormElementLvl1("Ticker Symbol", "textField").sendKeys(tyckersymbol);
	}

}
