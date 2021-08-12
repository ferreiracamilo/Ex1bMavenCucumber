package testNGclasses;

import java.util.Hashtable;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;

import pageObjects.PoAppPage;
import pageObjects.PoAppPageForm;
import pageObjects.PoLandingPage;
import pageObjects.PoLoginPage;
import resources.Base;

public class ServiceAccount extends Base{

	/**
	* Region "Steps" AKA TestNG tags
	*/
	
	@BeforeMethod
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
    	
    	clickOnWaffleAndAccessService();
	}
		
	@AfterMethod
	public void close_browser(){
		driver.close();
	}
	
	@Test (priority = 1)
	public void tcPositiveCreateFullAccountRecord(){
		eraseAllAccountRecords();
		clickOnNewButton();
		inputFullRecordAndName("1stAccount");
		clickOnSaveButton();
		handleSuccessNotification();
	}
	
	@Test (priority = 2)
	public void tcNegativeVoidAccountName (){
		clickOnNewButton();
		clickOnSaveButton();
		PoAppPageForm form = new PoAppPageForm(driver);
		WebElement errorMandatoryField = form.getMandatoryNotFillError();
		Assert.assertEquals(errorMandatoryField.isDisplayed(), true);
	}
	
	@Test (priority = 3)
	public void tcModifyAccountRecordValuesRUT (){
		editSpecificAccountFromAccountsTab("1stAccount");
		modifyRatingUpsellTypeCombos("Cold","Yes","Other");
		clickOnSaveButton();
		handleSuccessNotification();
		editSpecificAccountFromAccountsTab("1stAccount");
		userValidatesModifiedValues("Cold","Yes","Other");
	}
	
	@Test (priority = 4)
	public void tcNegativeInvalidEmployeeValue () {
		editSpecificAccountFromAccountsTab("1stAccount");
		modifyEmployeesField("1431655766");
		clickOnSaveButton();
		warningEmployeeInput("Employees: value outside of valid range on numeric field: 1431655766");
	}
	
	@DataProvider
	public Object [][] getData(){
		Object [][] data = new Object [4][4];
		
		//1st dataset
		data[0][0] = "BROU";
		data[0][1] = "2481-9098";
		data[0][2] = "www.brou.com.uy";
		data[0][3] = "BROUUY";
		
		//2nd dataset
		data[1][0] = "SANTANDER";
		data[1][1] = "2478-9098";
		data[1][2] = "www.santander.com.uy";
		data[1][3] = "SANTUY";
		
		//3rd dataset
		data[2][0] = "BBVA";
		data[2][1] = "2481-8298";
		data[2][2] = "www.bbva.com.uy";
		data[2][3] = "BBVAUY";
		
		//4th dataset
		data[3][0] = "ITAU";
		data[3][1] = "2481-9698";
		data[3][2] = "www.itau.com.uy";
		data[3][3] = "ITAUUY";
		
		return data;
	}
	
	@Test (priority = 5,dataProvider="getData")
	public void tcPositiveMultipleAccounts (String accountName, String phoneNumber, String websiteName, String tyckerSymbol) throws Throwable {
		clickOnNewButton();
		inputAccNamePhoneWebTycker (accountName,phoneNumber,websiteName,tyckerSymbol);
		clickOnSaveButton();
		handleSuccessNotification();
	}
	
	/**
	* Aux methods
	*/
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
	
	public void clickOnNewButton(){
		PoAppPage pageTab = new PoAppPage(driver);
		pageTab.waitUntilURLTabLoaded(); //temporary
		
		WebElement newBtn = pageTab.getNewBtn("New");
		pageTab.waitElement(newBtn);
		newBtn.click();
		PoAppPageForm form = new PoAppPageForm(driver);
		form.waitUntilFormLoaded(); //verify form loaded successfully
	}
	
	/**
	* Create an account record with full fields
	* @param arg1 account name to be assigned
	* @param WebDriver driver instance
	*/
	public void inputFullRecordAndName (String arg1){
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
	
	public void clickOnSaveButton(){
		PoAppPageForm form = new PoAppPageForm(driver);
	    form.getFormBtnSave().click();
	}
	
	public void handleSuccessNotification(){
		PoAppPage tab = new PoAppPage(driver);
		WebElement successDialogBTN = tab.getcloseConfirmSucessBTN();
		tab.waitElement(successDialogBTN);
		Assert.assertEquals(successDialogBTN.isDisplayed(), true);
		successDialogBTN.click();
	}
	
	//user_click_menu_arrow_down_and_click_Edit_button_from_row_which_contains
	public void editSpecificAccountFromAccountsTab (String arg1){
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
	
	public void modifyRatingUpsellTypeCombos(String arg1, String arg2, String arg3){
		PoAppPageForm formEditAccount = new PoAppPageForm(driver);
		//Modifying comboRating
		WebElement comboRating = formEditAccount.getFormElementLvl1("Rating", "comboBox");
		formEditAccount.moveNclick(comboRating);
		WebElement optionRating = formEditAccount.getFormComboItemLvl2("Rating", arg1);
		optionRating.click();
		
		//Modifying comboType
		WebElement comboType = formEditAccount.getFormElementLvl1("Type", "comboBox");
		formEditAccount.moveNclick(comboType);
		WebElement optionType = formEditAccount.getFormComboItemLvl2("Type", arg3);
		optionType.click();
		
		//Modifying comboUpsell
		WebElement comboUpsell = formEditAccount.getFormElementLvl1("Upsell Opportunity", "comboBox");
		formEditAccount.moveNclick(comboUpsell);
		WebElement optionUpsell = formEditAccount.getFormComboItemLvl2("Upsell Opportunity", arg2);
		optionUpsell.click();
	}
	
	public void userValidatesModifiedValues (String strArg1, String strArg2, String strArg3){
		
		System.out.println("Value Rating should be " + strArg1);
		System.out.println("Value Upsell Opportunity should be " + strArg2);
		System.out.println("Value Type should be " + strArg3);
		
		PoAppPageForm formEditAccount = new PoAppPageForm(driver);
		
		//Verifying comboRating
		WebElement comboRating = formEditAccount.getFormElementLvl1("Rating", "comboBox");
		formEditAccount.waitElement(comboRating);
		formEditAccount.moveNclick(comboRating);
		String valueRating = formEditAccount.getFormComboItemSelectedLvl2("Rating").getText();

		//Modifying comboType
		WebElement comboType = formEditAccount.getFormElementLvl1("Type", "comboBox");
		formEditAccount.waitElement(comboType);
		formEditAccount.moveNclick(comboType);
		String valueType = formEditAccount.getFormComboItemSelectedLvl2("Type").getText();
		
		//Modifying comboUpsell
		WebElement comboUpsell = formEditAccount.getFormElementLvl1("Upsell Opportunity", "comboBox");
		formEditAccount.waitElement(comboUpsell);
		formEditAccount.moveNclick(comboUpsell);
		String valueUpsell = formEditAccount.getFormComboItemSelectedLvl2("Upsell Opportunity").getText();
		
		System.out.println("Value Rating is " + valueRating);
		System.out.println("Value Upsell Opportunity is " + valueUpsell);
		System.out.println("Value Type is " + valueType);
		
		Assert.assertEquals(valueRating , strArg1);
		Assert.assertEquals(valueUpsell , strArg2);
		Assert.assertEquals(valueType , strArg3);
		
		formEditAccount.getFormBtnCancel().click();
    }
	
	public void modifyEmployeesField (String arg1){
	    PoAppPageForm accountForm =  new PoAppPageForm (driver);
	    String value = arg1;
	    WebElement employeesField = accountForm.getFormElementLvl1("Employees", "textField");
	    accountForm.eraseTextField(employeesField);
	    accountForm.getFormElementLvl1("Employees", "textField").sendKeys(value);
	}
	
	public void warningEmployeeInput(String arg1) {
		PoAppPageForm form = new PoAppPageForm(driver);
		WebElement errorEmployeesField = form.getNumericEmpError();
		Assert.assertEquals(errorEmployeesField.isDisplayed(), true);
	}
	
	public void inputAccNamePhoneWebTycker (String accountname, String phonenumber, String websitename, String tyckersymbol) {
			PoAppPageForm accountForm = new PoAppPageForm (driver);
			
			accountForm.getFormElementLvl1("*Account Name", "textField").sendKeys(accountname);
			accountForm.getFormElementLvl1("Phone", "textField").sendKeys(phonenumber);
			accountForm.getFormElementLvl1("Website", "textField").sendKeys(websitename);
			accountForm.getFormElementLvl1("Ticker Symbol", "textField").sendKeys(tyckersymbol);
	}
	
	public void clickOnWaffleAndAccessService(){
		//LANDING PAGE
		PoLandingPage poLP = new PoLandingPage(driver);
    	poLP.getWaffleMenuButton().click(); //click on waffle menu button
    	poLP.getWaffleApp("Service").click(); //clicks on service app button from waffle menu
    	
    	//APP PAGE
    	PoAppPage poAP = new PoAppPage(driver); 
    	poAP.waitUntilURLAppLoaded(); //Verify Service app has been loaded
    	//>ACCESS ACCOUNTS TAB 
	    poAP.getOneTab("Accounts").click();
	    poAP.waitUntilURLTabLoaded();
	}
	
}
