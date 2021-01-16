package myapps;

import id.aldochristiaan.salad.Salad;
import id.aldochristiaan.salad.util.Driver;
import id.aldochristiaan.salad.util.LogLevel;
import id.aldochristiaan.salad.util.LogUtil;
import id.aldochristiaan.salad.util.PropertiesLoader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.util.Objects;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {
                "src/test/resources/features",
        },
        stepNotifications = true,
        glue = "StepDefinition",
        plugin = {
                "pretty",
                "rerun:rerun/failed_scenarios.txt",
                "json:target/cucumber-report.json",
        })

 public class CucumberTestRunner {
    private static Salad salad; // Automation Engine
    private static AndroidDriver<AndroidElement> androidDriver;
    protected static myapps myapps;

    @BeforeAll // Junit5 annotation
    public static void setUp() { // To start appium server and inject elements
        String elementPropertiesDirectory = "src/test/resources/elements/"; // Element properties file
        String capabilitiesFileName = "capabilities.properties";
        // You can choose other constructor to run automation
        salad = new Salad(PropertiesLoader.loadCapabilities(capabilitiesFileName),
                elementPropertiesDirectory,
                Driver.ESPRESSO,
                LogLevel.DEBUG); // or Platform.IOS
        salad.start();
        myapps = new myapps(salad.getAndroidDriver()); // or salad.getIosDriver();
    }

    @AfterAll // J
    public static void tearDown() {
        salad.stop(Driver.ESPRESSO); // or Platform.IOS
    }

    public static void resetApp() {
        salad.stop(Driver.ESPRESSO); // or Platform.IOS
    }

    public static void takeScreenshot(String filename) {
        File srcFile = (File) salad.getAndroidDriver().getScreenshotAs(OutputType.FILE);
        File imageFile = new File("screenshot/" + filename + ".png");
        try{
            FileUtils.copyFile(Objects.requireNonNull(srcFile), imageFile);
            LogUtil.info("Screenshot Taken");
        }
        catch (Exception e){
            LogUtil.error("Exception while taking screenshot", e);
        }
        salad.stop(Driver.ESPRESSO); // or Platform.IOS
    }

}
