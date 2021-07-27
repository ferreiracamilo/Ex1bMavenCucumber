package stepDefinitions;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import cucumber.api.java.en.*;
import cucumber.api.junit.Cucumber;
import pageObjects.PoAppPage;
import pageObjects.PoAppPageForm;
import pageObjects.PoLandingPage;
import pageObjects.PoLoginPage;
import resources.DriverFactory;

//@RunWith(Cucumber.class)
public class NavigateTabs extends DriverFactory {

	/**
	* Background section
	*/
	@Given("^Initialize the browser with \"([^\"]*)\"$")
    public void initialize_the_browser_with_something(String strArg1) throws Throwable {
        //Execute
		driver = initializeDriver(strArg1);
    }

    @And("^User navigates to \"([^\"]*)\" Site$")
    public void user_navigates_to_something_site(String strArg1) throws Throwable {
    	//Execute
    	driver.get(strArg1);
    }
  
    @And("^User enters credentials and logs in$")
    public void user_enters_credentials_and_logs_in() throws Throwable {
    	//Execute
    	PoLoginPage poLP =new PoLoginPage(driver);
    	String user = poLP.getUserCredential("user");
    	String password = poLP.getUserCredential("password");
    	
    	poLP.getUserID().sendKeys(user);
    	poLP.getPassword().sendKeys(password);
    	poLP.getPassword().submit();
    }
    
    /**
	* Scenario section
	*/
    @Given("^User clicks on waffle menu button$")
    public void user_clicks_on_waffle_menu_button() throws Throwable {
    	//Execute
    	PoLandingPage poLP = new PoLandingPage(driver);
    	poLP.getWaffleMenuButton().click();
    	System.out.println("Click on waffle menu button");
    }
    
    @And("^User clicks on \"([^\"]*)\" button$")
    public void user_clicks_on_something_button(String strArg1) throws Throwable {
    	//Execute
    	PoLandingPage poLP = new PoLandingPage(driver);
    	poLP.getWaffleApp(strArg1).click();
    	System.out.println("Click on Service app");
    }

    @When("^User clicks on every tab$")
    public void user_clicks_on_every_tab() throws Throwable {
    	//Execute
    	PoAppPage poAP = new PoAppPage(driver);
    	for (WebElement tab : poAP.getTabOptions()) {
    		tab.click();
    	}
    	System.out.println("Click on every tab");
    }

    @And("^User clicks on every new and cancel button$")
    public void user_clicks_on_every_new_and_cancel_button() throws Throwable {
        //Execute
    	PoAppPage poAP = new PoAppPage(driver);
    	PoAppPageForm poPF =new PoAppPageForm(driver);
    	
    	//Access "Accounts" tab and complete actions
    	poAP.getOneTab("Accounts").click();
    	poAP.getNewBtn("New").click();
    	System.out.println("Hice click en boton new");
    	poPF.getFormBtnCancel().click();
    	System.out.println("Hice click en cancel");
    	
    	//Access "Contacts" tab and complete actions
    	poAP.getOneTab("Contacts").click();
    	Thread.sleep(30);
    	poAP.getNewBtn("New").click();
    	Thread.sleep(30);
    	poPF.getFormBtnCancel().click();

    	//Access "Cases" tab and complete actions
    	poAP.getOneTab("Cases").click();
    	poAP.getNewBtn("New").click();
    	poPF.getFormBtnCancel().click();
    	
    	//Access "Reports" tab and complete actions
    	poAP.getOneTab("Reports").click();
    	poAP.getNewBtn("New Report").click();
    	driver.switchTo().frame(poAP.getiFrameWindow());
    	poAP.getiFrameCancelBTN().click();
    	driver.switchTo().defaultContent();
    	
    	//Access "Dashboards" tab and complete actions
    	poAP.getOneTab("Dashboards").click();
    	poAP.getNewBtn("New Dashboard").click();
    	driver.switchTo().frame(poAP.getiFrameWindow());
    	poAP.getiFrameCancelBTN().click();
    	driver.switchTo().defaultContent();
    	
    	System.out.println("Click on every tab that has new button, click on each new and cancel");
    }
    
    @Then("^Close browser$")
    public void close_browser() throws Throwable {
    	//Execute
    	driver.close();
    	System.out.println("Close driver");
    }
    
}
