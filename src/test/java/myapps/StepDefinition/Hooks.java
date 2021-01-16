package myapps.StepDefinition;

import id.aldochristiaan.salad.util.LogUtil;
import io.cucumber.core.api.Scenario;
import io.cucumber.java8.En;
import myapps.TestInstrument;

import static myapps.CucumberTestRunner.setUp;


public class Hooks extends TestInstrument implements En {

        //private  static final HashMap<String>, Boolean> featureMap = HashMap<~>();
        private static  String lastScenarioStatus = "failed";

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
                        LogUtil.info("Scenario '"+scenario.getName()+"' with status "+scenario.getStatus().lowerCaseName());
                        LogUtil.info("-----------------End of Scenario-----------------");
                        setLastScenarioStatus(scenario.getStatus().lowerCaseName());
                });

                After(1, this::afterScenario);
        }

        private boolean isFeatureNameNotExist(Scenario scenario) {

                return false;
        }

}
//        private static Salad salad; // Automation Engine
//        private static AndroidDriver<AndroidElement> androidDriver;
//        protected static MyApps myapps;
//
//        @BeforeAll // Junit5 annotation
//        public static void setUp() { // To start appium server and inject elements
//                String elementPropertiesDirectory = "src/test/resources/elements/"; // Element properties file
//                String capabilitiesFileName = "capabilities.properties";
//                // You can choose other constructor to run automation
//                salad = new Salad(PropertiesLoader.loadCapabilities(capabilitiesFileName),
//                        elementPropertiesDirectory,
//                        Driver.ESPRESSO,
//                        LogLevel.DEBUG); // or Platform.IOS
//                salad.start();
//                myapps = new MyApps(salad.getAndroidDriver()); // or salad.getIosDriver();
//        }
//
//        @AfterAll // J
//        public static void tearDown() {
//                salad.stop(Driver.ESPRESSO); // or Platform.IOS
//        }
//
//        public static void resetApp() {
//                salad.stop(Driver.ESPRESSO); // or Platform.IOS
//        }
//
//        public static void takeScreenshot(String filename) {
//                File srcFile = (File) salad.getAndroidDriver().getScreenshotAs(OutputType.FILE);
//                File imageFile = new File("screenshot/" + filename + ".png");
//                try{
//                        FileUtils.copyFile(Objects.requireNonNull(srcFile), imageFile);
//                        LogUtil.info("Screenshot Taken");
//                }
//                catch (Exception e){
//                        LogUtil.error("Exception while taking screenshot", e);
//                }
//                salad.stop(Driver.ESPRESSO); // or Platform.IOS
//        }
//}
