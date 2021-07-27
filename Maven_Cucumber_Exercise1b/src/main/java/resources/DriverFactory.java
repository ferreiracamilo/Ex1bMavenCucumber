package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverFactory {
	public WebDriver driver;
	public WebDriverWait w; 
	//public Properties prop; //Will be commented until prop are being use within InitilizeDriver

	 /**
	* Initialize driver
	* @param browserName // values -> chrome,firefox,opera
	*/
	public WebDriver initializeDriver(String browserName) throws IOException {

		/*
		 //CHANGED TO BE DONE THROUGH ARGUMENT IN CUCUMBER
		 
		prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\data.properties");

		prop.load(fis);
		String browserName = prop.getProperty("browser");
		System.out.println("Browser running is" + browserName); //can be commented, could be a potential log4j in future
		
		*/
		
		
		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\resources\\chromedriver.exe"); //drivertype and driver exe location
			
			//Set option to take out notification
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			
			driver = new ChromeDriver(options); //create chromedriver using as argument options created
		} else if (browserName.equals("firefox")) {
			
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\resources\\geckodriver.exe"); //drivertype and driver exe location
			
			//Set option to take out notification
			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(new FirefoxProfile());
			options.addPreference("dom.webnotifications.enabled", false);
			
			driver = new FirefoxDriver(options);
			// firefox code
		} else if (browserName.equals("opera")) {
			System.setProperty("webdriver.opera.driver", System.getProperty("user.dir")+"\\resources\\operadriver.exe"); //drivertype and driver exe location
			
			//Set option to take out notification
			OperaOptions options = new OperaOptions();
			options.addArguments("--disable-notifications");
			
			driver = new OperaDriver(options);
		}

		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		w = new WebDriverWait(driver, 5000);
		return driver;

	}
	
	public void getScreenshot(String result) throws IOException
	{
		//In future occasions may be use
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File("C://test//"+result+"screenshot.png")); //to be decided location to save screenshots in case is done
	}
	
}

