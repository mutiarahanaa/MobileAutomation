package myapps.PageObject;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;



public class HomepagePO extends Basepage {
    public HomepagePO(AndroidDriver<AndroidElement> androidDriver) {
        super(androidDriver);
    }

    public void validateHonepage(){
        validateDisplayed("ICONSEARCH", 5);

    }

}
