
package myapps.StepDefinition.myapps;

import io.cucumber.java8.En;
import myapps.TestInstrument;


public class HomepageStep extends TestInstrument implements En{
        public HomepageStep() {
            Then("user is in homepage", () -> {
                // Write code here that turns the phrase above into concrete actions

                myapps.homePO().wait(1000);
                myapps.homePO().validateHomepage();
                System.out.println("test test test");
            });
            And("^user type \"([^\"]*)\"$", (String name) -> {
                myapps.homePO().typeSearch(name);
            });

        }

}