package myapps;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "classpath:features",
        },
        stepNotifications = true,
        glue = "StepDefinitions",
        plugin = {
                "pretty",
                "rerun:rerun/failed_scenarios.txt",
                "json:target/cucumber-report.json",
        }
//        plugin = {"pretty", "json:target/cucumber-report.json"},
//        features = "src/test/resources/features",
//        glue = "StepDefinition",
//        tags = ""
)


 public class CucumberRunner extends MyAppFactory{
//    @Test(groups = "TestNG", description = "TestNG cucumber")
//    public void runCukes() {
//        new TestNGCucumberRunner(getClass()).runCukes();
//    }
//
//    @After
//    public void afterScenario() {
//        if (driver != null) {
//            driver.resetApp();
//        }
//
//        if (webDriver != null) {
//            webDriver.manage().deleteAllCookies();
//        }
//    }

}
