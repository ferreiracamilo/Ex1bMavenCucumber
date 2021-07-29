package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
	private static final String formComboBox = "//label[.='$LabelName'] /following-sibling::div //lightning-base-combobox";
	private static final String formTextField = "//label[.='$LabelName'] /following-sibling::div //input";
	private static final String formTextArea = "//label[.='$LabelName'] /following-sibling::div //textarea";
	private static final String formDefault = "//label[.='$LabelName'] /following-sibling::div"; //valid for calendar and searchBox elements
	
	//Interactable elements level 2, depends on clicking a previous element
	private static final String formComboItem = "//label[.='$LabelName'] /following-sibling::div //lightning-base-combobox-item[.='$ComboName']";
	private static final String formComboItems = "//label[.='$LabelName'] /following-sibling::div //lightning-base-combobox-item";
	private static final String formCalendarItems = "//label[.='$LabelName'] /following-sibling::div //span[@class='slds-day']";
	private static final String formSearchBoxOption = "//label[.='$LabelName'] /following-sibling::div //span[@class='slds-media__body'][.='$OptionName']";
	private static final String formSearchBoxOptions = "//label[.='$LabelName'] /following-sibling::div //span[@class='slds-media__body']";
	//SUPRESS IF NEXT WORKS private static final String formComboItemIsSelected = "//label[.='$LabelName'] /following-sibling::div //lightning-base-combobox-item[.='$OptionName'] //span[@class='slds-mediafigure slds-listboxoption-icon'] //lightning-icon";
	private static final String formComboItemSelected = "//label[.='$LabelName'] /following-sibling::div //lightning-base-combobox-item //span//lightning-icon //ancestor::lightning-base-combobox-item";
	
	
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
		this.wait =new WebDriverWait(this.driver, 40);
		PageFactory.initElements(driver, this);
	}
	
	/**
	* Region Getters
	*/
		
	/**
	* Get a WebElement warning error to comply Exercise 1b - 6
	* <b>Obtain this webelement after input '1431655766' into Employees input within Account form</b>
	* @return WebElement will be <b>null otherwise</b>
	*/
	public WebElement getnumericEmpError () {
		return numericEmpError;
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
	public WebElement getFormElement (String labelName, String fieldType) {
		WebElement ret = null;
		switch(fieldType.trim().toLowerCase()){ 
			//String trim and lowercase to ensure max possibilities to match with values below
			case "combobox":
				ret = driver.findElement(By.xpath(formComboBox.replace("$LabelName", labelName)));
				break;
			case "textfield":
				ret = driver.findElement(By.xpath(formTextField.replace("$LabelName", labelName)));
				break;
			case "textarea":
				ret = driver.findElement(By.xpath(formTextArea.replace("$LabelName", labelName)));
				break;
			case "calendar":
				ret = driver.findElement(By.xpath(formDefault.replace("$LabelName", labelName)));
				break;
			case "searchBox":
				ret = driver.findElement(By.xpath(formDefault.replace("$LabelName", labelName)));
				break;
		}
		return ret;
	}
	
	/**
	* Get elements available after clicking a dropdown/combobox 
	* <br><b>You must click on dropdown/combobox before using this method</b>
	* @param labelName Specifiy name of the label above the field you want to reach
	*/
	public List <WebElement> getFormComboItems (String labelName) {
		List <WebElement> ret = driver.findElements(By.xpath(formComboItems.replace("$LabelName", labelName)));
		return ret;
	}
	
	/**
	* Click on a specific option from dropdown options
	* <br><b>You must click on a dropdown before using this method</b>
	* @param labelName Specifiy name of the label above the field you want to reach
	* @param comboOption Specify name of the option from dropdown to click on
	*/
	public WebElement getFormComboItem (String labelName, String comboOption) {
		String path = formComboItem;
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
	public WebElement getFormComboItemSelected (String labelName) {
		WebElement ret = driver.findElement(By.xpath(formComboItemSelected.replace("$LabelName", labelName)));
		return ret;
	}
	
	/**
	* Get elements available after clicking a calendar input
	* <br><b>You must click on calendar before using this method</b>
	* @param labelName Specifiy name of the label above the field you want to reach
	* @return WebElement List will contain all days displayed in calendar (usually shows a few days from prev and next months along all days from month selected)
	*/
	public List <WebElement> getFormCalendarItems (String labelName) {
		List <WebElement> ret = driver.findElements(By.xpath(formCalendarItems.replace("$LabelName", labelName)));
		return ret;
	}
	
	/**
	* Get elements available after clicking a searchbox
	* <br><b>You must click on searchbox before using this method</b>
	* @param labelName Specifiy name of the label above the field you want to reach
	*/
	public List <WebElement> getSuggestedTextOptions (String labelName) {
		List <WebElement> ret = driver.findElements(By.xpath(formSearchBoxOptions.replace("$LabelName", labelName)));
		return ret;
	}
	
	/**
	* Click on a specific option from searchBox options
	* <br><b>You must click on a searchBox before using this method</b>
	* @param labelName Specifiy name of the label above the field you want to reach
	* @param optionName Specify name of the option from searchBox to click on
	*/
	public WebElement getSuggestedTextOption (String labelName, String optionName) {
		String path = formSearchBoxOption;
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
	
}
