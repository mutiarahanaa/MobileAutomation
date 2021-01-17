package myapps;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/features/",
        },
        stepNotifications = true,
        glue = "StepDefinition",
        plugin = {
                "pretty",
                "rerun:rerun/failed_scenarios.txt",
                "json:target/cucumber-report.json",
        })

 public class CucumberTestRunner {
}
