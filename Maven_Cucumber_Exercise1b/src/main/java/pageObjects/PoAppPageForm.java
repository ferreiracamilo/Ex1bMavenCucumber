package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PoAppPageForm {
	
	/**
	* Region Variables
	*/
	public WebDriver driver;
	
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
	
	@FindBy(xpath="//button[@name='CancelEdit' or @title='Cancel']")
	  private WebElement formBtnCancel;
	
	@FindBy(xpath="//button[@name='SaveAndNew']")
	  private WebElement formBtnSaveNew;
	
	@FindBy(xpath="//button[@name='SaveEdit']")
	  private WebElement formBtnSave;
	
	/**
	* Region Constructor
	*/
	public PoAppPageForm(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	/**
	* Region Getters
	*/
	
	public WebElement getFormBtnCancel() {
		return formBtnCancel;
	}
	
	public WebElement getFormBtnSaveNew() {
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
	* @param driver Specify driver to be used
	*/
	public void clickCorrected (WebElement element, WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
		element.click();
	}
	
}
