package testNGclasses;

import org.openqa.selenium.WebElement;
import org.testng.annotations.*;

import cucumber.api.java.en.Given;
import pageObjects.PoAppPage;
import pageObjects.PoAppPageForm;
import pageObjects.PoLandingPage;
import pageObjects.PoLoginPage;
import resources.Base;

public class Service extends Base{
	
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
	}
		
	@BeforeMethod
	public void user_clicks_on_waffle_menu_button() throws Throwable {
		//LANDING PAGE
		PoLandingPage poLP = new PoLandingPage(driver);
    	poLP.getWaffleMenuButton().click(); //click on waffle menu button
    	poLP.getWaffleApp("Service").click(); //clicks on service app button from waffle menu
    	
    	//APP PAGE
    	PoAppPage poAP = new PoAppPage(driver); 
    	poAP.waitUntilURLAppLoaded(); //Verify Service app has been loaded
	}
	
	@Test
	public void user_clicks_every_tab_click_new_and_cancel_if_applies() throws Throwable {
		//Order6
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
	
	@AfterClass
	public void close_browser() throws Throwable {
		driver.close();
	}
	
}
