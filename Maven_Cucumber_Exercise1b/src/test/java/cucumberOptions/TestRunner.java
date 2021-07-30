package cucumberOptions;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@RunWith(Cucumber.class)
@CucumberOptions(  
	    features = "src/test/java/features",
	    glue="stepDefinitions"
)

/* 
 to use @do tags implement
 
 
 @CucumberOptions(  
	    features = "src/test/java/features",
	    glue="stepDefinitions",
	    tags = "@do")
 * */

public class TestRunner extends AbstractTestNGCucumberTests  {

}
