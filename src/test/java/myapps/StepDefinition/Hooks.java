package myapps.StepDefinition;

import id.aldochristiaan.salad.util.LogUtil;
import io.cucumber.core.api.Scenario;
import io.cucumber.core.event.Status;
import io.cucumber.java.Before;
import io.cucumber.java8.En;
import myapps.TestInstrument;

import java.util.HashMap;


public class Hooks extends TestInstrument implements En {

        private static final HashMap<String, Boolean> featureMap = new HashMap<String, Boolean>();

        private static Status lastScenarioStatus = Status.FAILED;



        public Hooks () {

                Before(0, () -> {
                        setUp();
                        Runtime.getRuntime().addShutdownHook(new Thread(this::tearDown));
                });

                Before(1, (Scenario scenario) -> {
                        if (isFeatureNameNotExist(scenario)) {
                                if (getStateOfFeature()) {
                                        LogUtil.error("reset session");
                                        resetSession();
                                }else {
                                        replaceValueState(false, true);
                                }
                        } else {
                                if(getLastScenarioStatus().equals("failed") && isSessionNull()){
                                        LogUtil.info("Previous scenario is failed and session is disconnected");
                                        resetSession();
                                }else {
                                        LogUtil.info("Continue with existing session: " + getSessionId());
                                }
                        }
                        LogUtil.info("Running scenario : " + scenario.getName());
                        LogUtil.info("----------------Start of Scenario----------------");
                });

                After(0, (Scenario scenario) -> {
                        LogUtil.info("Scenario '"+scenario.getName()+"' with status "+scenario.getStatus());
                        LogUtil.info("-----------------End of Scenario-----------------");
                        setLastScenarioStatus(scenario.getStatus());
                });

                After(1, this::afterScenario);
        }

        private void resetSession() {
                removeSession();
                initiateSession();
        }

        private void setLastScenarioStatus( Status status){
                lastScenarioStatus = status;
        }

        private Status getLastScenarioStatus(){
                return lastScenarioStatus;
        }

        private boolean isFeatureNameNotExist(Scenario scenario) {

                return false;
        }

        @Before
        public static void beforeStep(Scenario mScenario) {
                scenario = mScenario;
        }
}
