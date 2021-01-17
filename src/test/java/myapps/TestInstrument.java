package myapps;


import id.aldochristiaan.salad.Salad;
import id.aldochristiaan.salad.util.Driver;
import id.aldochristiaan.salad.util.LogLevel;
import id.aldochristiaan.salad.util.LogUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.java8.Scenario;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class TestInstrument {

    //private List<TestResultStatus> testResultsStatus = new ArrayList<>();

    private static final HashMap<String, Boolean> state = new HashMap<String, Boolean>();
    public static Properties capabilitiesProperties = getCapabilitiesProperties();
    public static Scenario scenario;
    protected static myapps myapps;
    protected static AndroidDriver<AndroidElement> androidDriver;
    private static String scenarioName;
    private static boolean isRunning = false;
    private LogLevel appiumLogLevel;
    public static final Dotenv dotenv = Dotenv.load();


    private static Salad salad;

    private static Properties getCapabilitiesProperties() {
        Properties capabilitiesProperties = new Properties();
        try {
            FileInputStream properties = new FileInputStream("capabilities.properties");
            capabilitiesProperties.load(properties);
            properties.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return capabilitiesProperties;
    }

    private static void updateCapabilitiesFromSystemProp() {
        String value;
        for (Map.Entry<Object, Object> e : capabilitiesProperties.entrySet()) {
            value = (String) e.getValue();

            if (value.contains("{")) {
                value = value.replace('{', ' ');
                value = value.replace('}', ' ');
                value = System.getProperty(value.trim());
                capabilitiesProperties.setProperty((String) e.getKey(), value);
            }
        }
        LogUtil.info(capabilitiesProperties.toString());
    }

    public void initiateSession() {
        salad.start();

        androidDriver = salad.getAndroidDriver();
        myapps = new myapps(androidDriver);
    }

    public String getSessionId() {
        return androidDriver.getSessionId().toString();
    }

    public void removeSession() {
        salad.stop(Driver.ESPRESSO);
        //salad.removeSession(Driver.ESPRESSO);
    }

    protected Boolean getStateOfFeature() {
        return state.get("state");
    }

    protected void replaceValueState(Boolean oldvalue, Boolean newvalue) {
        state.replace("state", oldvalue, newvalue);
    }

    public Boolean isSessionNull(){
        boolean sessNull;
        try {
            if(!getSessionId().equals("null") || !getSessionId().equals("")){
                sessNull = false;
            }else sessNull = true;
        }catch (NullPointerException e){
            sessNull = true;
        }
        LogUtil.info("isSessionNull : "+sessNull);
        return sessNull;
    }


    public void setUp() {
        String elementPropertiesDirectory;
        updateCapabilitiesFromSystemProp();
        LogUtil.info(capabilitiesProperties.toString());

        elementPropertiesDirectory = "src/test/resources/elements/";

        if (!isRunning) {
            salad = getSaladInstance(capabilitiesProperties, elementPropertiesDirectory);
            LogUtil.info("capabilities : " + capabilitiesProperties);
            salad.start();
            isRunning = true;
            initiateSession();
            state.put("state", false);
            LogUtil.info("Appium server is already started!");
            if (salad.getAndroidDriver() == null) initiateSession();
        }

    }

    private Salad getSaladInstance(Properties capabilitiesProperties,String elementPropertiesDirectory) {
        Salad salad = null;
        String appiumHost = "appiumLocalHost";
        if(capabilitiesProperties.containsKey(appiumHost)) {
            LogUtil.info("Running test using Appium Local Server");
            String host = String.format("http://%s", System.getProperty(appiumHost));
            salad = new Salad(capabilitiesProperties, elementPropertiesDirectory, Driver.ESPRESSO, host);
        } else {
            LogUtil.info("Running test using Appium Service Builder");
            setAppiumLogLevel();
            salad = new Salad(capabilitiesProperties, elementPropertiesDirectory, Driver.ESPRESSO, appiumLogLevel);
        }
        return salad;
    }

    private void setAppiumLogLevel() {
        try {
            appiumLogLevel = LogLevel.valueOf(System.getProperty("logLevel"));
        } catch (Exception ex){
            appiumLogLevel = LogLevel.ERROR;
        }
    }

    public void afterScenario(Scenario scenario) {
        scenarioName = scenario.getName();

        if (scenario.isFailed()) {
            File srcFile = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
            byte[] screenshot = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.BYTES);
            File imageFile = new File(dotenv.get("SCREENSHOT_PATH") + "/" + scenarioName + ".png");
            try {
//                scenario.embed(screenshot,"image/png");
                FileUtils.copyFile(Objects.requireNonNull(srcFile), imageFile);
                LogUtil.info("Screenshot taken");
                //commenting this for debug purpose
            } catch (Exception e) {
                LogUtil.error("Exception while taking screenshot", e);
            }
            androidDriver.resetApp();
        }
    }

    public void tearDown() {
        salad.stop(Driver.ESPRESSO);
        if (capabilitiesProperties.getProperty("avd") != null) {
            String adbPath = "/Users/" + System.getProperty("user.name") + "/library/android/sdk/platform-tools"
                    + File.separator + "adb";
            String[] killAndroidEmulator = new String[]{adbPath, "-s", "emulator-" + capabilitiesProperties.getProperty("port"), "emu", "kill"};
            runCommand(killAndroidEmulator);
        }
        removeSession();

    }

    private void runCommand(String[] command) {
        try {
            Process process = new ProcessBuilder(command).start();
            process.waitFor(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            LogUtil.error("No Command executed!", e);
        }
    }


//    private enum testResultStatus {
//        SUCCESSFUL, ABORTED, FAILED, DISABLED;
//    }

//    @Override
//    public void testDisabled(ExtensionContext context, Optional<String> reason) {
//        LogUtil.warn("Test Disabled for test " + context.getDisplayName() + ": with reason :- " + reason.orElse("No reason"));
//        testResultsStatus.add(TestResultStatus.DISABLED);
//    }
//
//    @Override
//    public void testSuccessful(ExtensionContext context) {
//        LogUtil.info("Test Successful for test : " + context.getDisplayName());
//        testResultsStatus.add(TestResultStatus.SUCCESSFUL);
//    }
//
//    @Override
//    public void testAborted(ExtensionContext context, Throwable cause) {
//        LogUtil.warn("Test Aborted for test : " + context.getDisplayName());
//        testResultsStatus.add(TestResultStatus.ABORTED);
//    }
//
//    @Override
//    public void testFailed(ExtensionContext context, Throwable cause) {
//        LogUtil.error("Test Failed for test : " + context.getDisplayName());
//        testResultsStatus.add(TestResultStatus.FAILED);
//        takeScreenshot(context.getDisplayName());
//        resetApp();
//    }
//
//    @Override
//    public void afterAll(ExtensionContext context) throws Exception {
//        Map<TestResultStatus, Long> summary = testResultsStatus.stream()
//                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
//        LogUtil.info("Test result summary for " + context.getDisplayName() + " " + summary.toString());
//    }
}
