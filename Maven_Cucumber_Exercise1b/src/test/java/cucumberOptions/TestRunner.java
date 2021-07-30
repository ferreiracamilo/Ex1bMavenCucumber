package cucumberOptions;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@RunWith(Cucumber.class)
@CucumberOptions(  
	    features = "src/test/java/features",
	    glue="stepDefinitions",
	    tags = "@do")
//In case there's a tag with @Skip in some scenario won't be executed

public class TestRunner extends AbstractTestNGCucumberTests  {

}
