
package myapps.StepDefinition;
import io.cucumber.java8.En;
import myapps.MyAppFactory;

public class HomepageStep extends MyAppFactory implements En{
        public HomepageStep() {
            Then("user is in homepage", () -> {
                // Write code here that turns the phrase above into concrete actions
                //myapps.homepagePO().wait(1000);
                myapps.homePage().validateHomepage();
            });
            And("^user type \"([^\"]*)\"$", (String name) -> {
                myapps.homePage().typeSearch(name);
            });

        }

}