package runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "pretty",
                },
        glue = {"steps","config"},
        features = {"src/test/resources/FeatureFiles"},
        tags = {"@first"}
)
public class RunnerClass {

}
