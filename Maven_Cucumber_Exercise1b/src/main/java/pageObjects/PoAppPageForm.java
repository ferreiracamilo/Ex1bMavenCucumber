package pageObjects;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PoAppPageForm {
	
	/**
	* Region Variables
	*/
	public WebDriver driver;
	public WebDriverWait wait;
	
	//Interactable elements level 1
	private static final String lvl1formComboBox = "//label[.='$LabelName'] /following-sibling::div //lightning-base-combobox";
	private static final String lvl1formTextField = "//label[.='$LabelName'] /following-sibling::div //input";
	private static final String lvl1formTextArea = "//label[.='$LabelName'] /following-sibling::div //textarea";
	private static final String lvl1formDefault = "//label[.='$LabelName'] /following-sibling::div"; //valid for calendar and searchBox elements
	
	//Interactable elements level 2, depends on clicking a Level 1 Element
	private static final String lvl2formComboItem = "//label[.='$LabelName'] /following-sibling::div //lightning-base-combobox-item[.='$ComboName']";
	private static final String lvl2formComboItems = "//label[.='$LabelName'] /following-sibling::div //lightning-base-combobox-item";
	private static final String lvl2formCalendarItems = "//label[.='$LabelName'] /following-sibling::div //span[@class='slds-day']";
	private static final String lvl2formSearchBoxOption = "//label[.='$LabelName'] /following-sibling::div //li //lightning-base-combobox-item /descendant-or-self::span[@class='slds-truncate' and .='$OptionName'][1]";
	private static final String lvl2formSearchBoxOptions = "//label[.='$LabelName'] /following-sibling::div //li //lightning-base-combobox-item /descendant-or-self::span[@class='slds-truncate'][1]";
	private static final String lvl2formComboItemSelected = "//label[.='$LabelName'] /following-sibling::div //lightning-base-combobox-item //span//lightning-icon //ancestor::lightning-base-combobox-item";
	//SUPRESS IF NEXT WORKS private static final String formComboItemIsSelected = "//label[.='$LabelName'] /following-sibling::div //lightning-base-combobox-item[.='$ComboOption'] //span//lightning-icon //ancestor::lightning-base-combobox-item";
	
	
	@FindBy(xpath="//button[contains(@class,'page-error-button')]")
	  private WebElement mandatoryNotFillError;
	
	//Obtain this webelement after input '1431655766' into Employees input within Account form
	@FindBy(xpath="//div[.='Employees: value outside of valid range on numeric field: 1431655766']")
	  private WebElement numericEmpError;
	
	//OLD PATH -> //button[@name='CancelEdit' or @title='Cancel']
	@FindBy(xpath="//div[contains(@class, 'windowViewMode-normal')] //button[@name='CancelEdit' or @title='Cancel']")
	  private WebElement formBtnCancel;
	
	//OLD PATH -> //button[@name='SaveAndNew']
	@FindBy(xpath="//div[contains(@class, 'windowViewMode-normal')] //button[@name='SaveAndNew' or @title='Save & New']")
	  private WebElement formBtnSaveNew;
	
	//OLD PATH -> //button[@name='SaveEdit']
	@FindBy(xpath="//div[contains(@class, 'windowViewMode-normal')] //button[@name='SaveEdit' or @title='Save']")
	  private WebElement formBtnSave;
	
	/**
	* Region Constructor
	*/
	public PoAppPageForm(WebDriver driver) {
		this.driver=driver;
		this.wait =new WebDriverWait(this.driver, 80);
		PageFactory.initElements(driver, this);
	}
	
	/**
	* Region Getters
	*/
		
	/**
	* Get a WebElement warning error to comply Exercise 1b - 6
	* <b>Error due to not comply with form rules related to Employees input
	* <b>Obtain this webelement after input '1431655766' into Employees input within Account form</b>
	*/
	public WebElement getNumericEmpError () {
		return numericEmpError;
	}
	
	/**
	* Get a WebElement warning
	* <b>Error due to not comply with form rules related to Mandatory fields empty
	*/
	public WebElement getMandatoryNotFillError () {
		return mandatoryNotFillError;
	}
	
	public WebElement getFormBtnCancel() {
		return formBtnCancel;
	}
	
	public WebElement getFormBtnSaveAndNew() {
		return formBtnSaveNew;
	}
	
	public WebElement getFormBtnSave () {
		return formBtnSave;
	}
	
	/**
	* Get a WebElement from  form
	* @param labelName Specifiy name of the label above the field you want to reach
	* @param fieldType Specify field type from following values: 
	* 									  <mark> comboBox, textField, textArea, calendar, searchBox </mark>
	* @return WebElement will be <b>null</b> if <b>fieldType doesn't match to values</b>
	*/
	public WebElement getFormElementLvl1 (String labelName, String fieldType) {
		WebElement ret = null;
		//switch(fieldType.trim().toLowerCase())
		String fieldTypeFix = fieldType.trim().toLowerCase();
		switch(fieldTypeFix){ 
			//String trim and lowercase to ensure max possibilities to match with values below
			case "combobox":
				ret = driver.findElement(By.xpath(lvl1formComboBox.replace("$LabelName", labelName)));
				break;
			case "textfield":
				ret = driver.findElement(By.xpath(lvl1formTextField.replace("$LabelName", labelName)));
				break;
			case "textarea":
				ret = driver.findElement(By.xpath(lvl1formTextArea.replace("$LabelName", labelName)));
				break;
			case "calendar":
				ret = driver.findElement(By.xpath(lvl1formDefault.replace("$LabelName", labelName)));
				break;
			case "searchbox":
				ret = driver.findElement(By.xpath(lvl1formDefault.replace("$LabelName", labelName)));
				break;
		}
		return ret;
	}
	
	/**
	* Get elements available after clicking a dropdown/combobox 
	* <br><b>You must click on dropdown/combobox before using this method</b>
	* @param labelName Specifiy name of the label above the field you want to reach
	*/
	public List <WebElement> getFormComboItemsLvl2 (String labelName) {
		List <WebElement> ret = driver.findElements(By.xpath(lvl2formComboItems.replace("$LabelName", labelName)));
		return ret;
	}
	
	/**
	* Click on a specific option from dropdown options
	* <br><b>You must click on a dropdown before using this method</b>
	* @param labelName Specifiy name of the label above the field you want to reach
	* @param comboOption Specify name of the option from dropdown to click on
	*/
	public WebElement getFormComboItemLvl2 (String labelName, String comboOption) {
		String path = lvl2formComboItem;
		path = path.replace("$LabelName", labelName);
		path = path.replace("$ComboName", comboOption);
		WebElement ret = driver.findElement(By.xpath(path));
		return ret;
	}
	
	/**
	* Get dropdown element selected from specific dropdown/combobox
	* <br><b>You must click on dropdown/combobox before using this method</b>
	* @param labelName Specifiy name of the label above the field you want to reach
	*/
	public WebElement getFormComboItemSelectedLvl2 (String labelName) {
		WebElement ret = driver.findElement(By.xpath(lvl2formComboItemSelected.replace("$LabelName", labelName)));
		return ret;
	}
	
	/**
	* Get elements available after clicking a calendar input
	* <br><b>You must click on calendar before using this method</b>
	* @param labelName Specifiy name of the label above the field you want to reach
	* @return WebElement List will contain all days displayed in calendar (usually shows a few days from prev and next months along all days from month selected)
	*/
	public List <WebElement> getFormCalendarItemsLvl2 (String labelName) {
		List <WebElement> ret = driver.findElements(By.xpath(lvl2formCalendarItems.replace("$LabelName", labelName)));
		return ret;
	}
	
	/**
	* Get elements available after clicking a searchbox
	* <br><b>You must click on searchbox before using this method</b>
	* @param labelName Specifiy name of the label above the field you want to reach
	*/
	public List <WebElement> getSuggestedTextOptionsLvl2 (String labelName) {
		List <WebElement> ret = driver.findElements(By.xpath(lvl2formSearchBoxOptions.replace("$LabelName", labelName)));
		return ret;
	}
	
	/**
	* Click on a specific option from searchBox options
	* <br><b>You must click on a searchBox before using this method</b>
	* @param labelName Specifiy name of the label above the field you want to reach
	* @param optionName Specify name of the option from searchBox to click on
	*/
	public WebElement getSuggestedTextOptionLvl2 (String labelName, String optionName) {
		String path = lvl2formSearchBoxOption;
		path = path.replace("$LabelName", labelName);
		path = path.replace("$OptionName", optionName);
		WebElement ret = driver.findElement(By.xpath(path));
		return ret;
	}
	
	/**
	* Region Methods
	*/
	
	/**
	* Alternative click to be applied which is highly recommended for form pages
	* @param element Specify WebElement to click on
	*/
	public void moveNclick (WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
		element.click();
	}
	
	public void waitElement (WebElement ele) {
		this.wait.until(ExpectedConditions.visibilityOf(ele));
	}
	
	/**
	* Verify form has loaded successfully
	* Depending on action (create or edit a record) link may differ, however in those two actions follows a pattern 
	*/
	public void waitUntilFormLoaded () {
		this.wait.until(ExpectedConditions.or(ExpectedConditions.urlContains("new?count"),ExpectedConditions.urlContains("edit?navigationLocation=LIST_VIEW_ROW")));
	}
	
	/**
	* Data for creating an account without data provider 
	* @return hashmap with key and values for txtFields
	*/
	public Hashtable valuesTxtFields () {
		Hashtable contenedor =new Hashtable();
		contenedor.put("*Account Name", "miNombreCuenta");
		contenedor.put("Phone", "24803250");
		contenedor.put("Fax", "24803251");
		contenedor.put("Account Number", "123456");
		contenedor.put("Website", "www.miwebsite.com");
		contenedor.put("Account Site", "cuentaSitio" );
		contenedor.put("Ticker Symbol", "MSFT"); //  ticker symbol or stock symbol, use the one from microsoft as example
		contenedor.put("Employees", "23");
		contenedor.put("Annual Revenue", "2600");
		contenedor.put("SIC Code", "sicCodigo");
		contenedor.put("Billing Zip/Postal Code", "11600");
		contenedor.put("Billing City", "Montevideo");
		contenedor.put("Billing State/Province", "Montevideo");
		contenedor.put("Billing Country", "Uruguay");
		contenedor.put("Shipping Zip/Postal Code", "11600");
		contenedor.put("Shipping City", "Montevideo");
		contenedor.put("Shipping State/Province", "Montevideo");
		contenedor.put("Shipping Country", "Uruguay");
		contenedor.put("SLA Serial Number", "12345");
		contenedor.put("Number of Locations", "12");
		return contenedor;
	}
	
	/**
	* Data for creating an account without data provider 
	* @return hashmap with key and values for txtAreas
	*/
	public Hashtable valuesTxtAreas () {
		Hashtable contenedor=new Hashtable();
		contenedor.put("Description", "We are a company which deliver service to people");
		contenedor.put("Billing Street", "Carlos Anaya 2668" );
		contenedor.put("Shipping Street", "Monte Caseros 2080");
		return contenedor;
	}
	
	/**
	* Data for creating an account without data provider 
	* @return hashmap with key and values for comboBoxes
	*/
	public Hashtable valuesComboBox () {
		Hashtable contenedor=new Hashtable();
		contenedor.put("Rating", "Hot");
		contenedor.put("Type", "Prospect");
		contenedor.put("Ownership", "Public");
		contenedor.put("Industry", "Agriculture");
		contenedor.put("Customer Priority", "High");
		contenedor.put("SLA", "Gold");
		contenedor.put("Upsell Opportunity", "Maybe");
		contenedor.put("Active", "No");
		return contenedor;
	}
	
	public void eraseTextField (WebElement textFieldEle) {
		textFieldEle.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
	}
	
}
