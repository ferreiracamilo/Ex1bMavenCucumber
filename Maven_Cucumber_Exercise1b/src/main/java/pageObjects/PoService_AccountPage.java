package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PoService_AccountPage {
	
	/**
	* Region Variables
	*/
	public WebDriver driver;
	
	//Interactable elements level 1
	private  static final String formTextField = "//label[.='$LabelName'] /following-sibling::div //lightning-base-combobox";
	private  static final String formTextArea = "//label[.='$LabelName'] /following-sibling::div //input";
	private  static final String formComboBox = "//label[.='$LabelName'] /following-sibling::div //textarea";
	private  static final String formCalendar = "//label[.='$LabelName'] /following-sibling::div ";
	//Interactable elements level 2, depends on clicking a previous element
	private  static final String formComboItems = "//label[.='$LabelName'] /following-sibling::div //lightning-base-combobox-item";
	private  static final String formCalendarItems = "//label[.='$LabelName'] /following-sibling::div //span[@class='slds-day']";
	
	@FindBy(xpath="//button[@name=\'CancelEdit\']")
	  private WebElement formBtnNew;
	
	@FindBy(xpath="//button[@name='SaveAndNew']")
	  private WebElement formBtnSaveNew;
	
	@FindBy(xpath="//button[@name='SaveEdit']")
	  private WebElement formBtnSave;
	
	/**
	* Region Constructor
	*/
	public PoService_AccountPage(WebDriver driver) {
		this.driver=driver;
	}
	
	/**
	* Region Getters
	*/
	
	public WebElement getFormBtnNew() {
		return formBtnNew;
	}
	
	public WebElement getFormBtnSaveNew() {
		return formBtnSaveNew;
	}
	
	public WebElement getFormBtnSave () {
		return formBtnSave;
	}
	
	/**
	* Get a WebElement from "New Account" form
	* @param labelName Specifiy name of the label above the field you want to reach
	* @param fieldType Specify field type from following values: 
	* 									  <mark> comboBox, textField, textArea, calendar </mark>
	* @return WebElement will be <b>null</b> if <b>fieldType doesn't match to values</b>
	*/
	public WebElement getFormElement (String labelName, String fieldType) {
		WebElement app = null;
		switch(fieldType.trim().toLowerCase()){ 
			//String trim and lowercase to ensure max possibilities to match with values below
			case "combobox":
				app = driver.findElement(By.xpath(formTextField.replace("$LabelName", labelName)));
			case "textfield":
				app = driver.findElement(By.xpath(formTextArea.replace("$LabelName", labelName)));
			case "textarea":
				app = driver.findElement(By.xpath(formComboBox.replace("$LabelName", labelName)));
			case "calendar":
				app = driver.findElement(By.xpath(formCalendar.replace("$LabelName", labelName)));
		}
		return app;
	}
	
	/**
	* Get elements available to select from a dropdown/combobox
	* <br><b>You must click in dropdown/combobox before using this method</b>
	* @param labelName Specifiy name of the label above the field you want to reach
	*/
	public List <WebElement> getFormComboItems (String labelName) {
		List <WebElement> ret = driver.findElements(By.xpath(formComboItems.replace("$LabelName", labelName)));
		return ret;
	}
	
	/**
	* Get elements available to select from a dropdown/combobox
	* <br><b>You must click in calendar before using this method</b>
	* @param labelName Specifiy name of the label above the field you want to reach
	*/
	public List <WebElement> getFormCalendarItems (String labelName) {
		List <WebElement> ret = driver.findElements(By.xpath(formCalendarItems.replace("$LabelName", labelName)));
		return ret;
	}
	
	
	

	
}
