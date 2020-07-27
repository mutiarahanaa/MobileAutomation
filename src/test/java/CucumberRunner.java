import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "classpath:features",
        },
        stepNotifications = true,
        plugin = {
                "pretty",
                "rerun:rerun/failed_scenarios.txt",
                "json:target/cucumber-report.json",
        })
 public class CucumberRunner {

}
