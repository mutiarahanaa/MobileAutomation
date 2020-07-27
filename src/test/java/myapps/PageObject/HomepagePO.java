package myapps.PageObject;
import id.aldochristiaan.salad.module.Espresso;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class HomepagePO extends Espresso {
    public HomepagePO(AndroidDriver<AndroidElement> androidDriver) {
        super(androidDriver);
    }

    public void validateHonepage(){
        validateElementVisible("ICONSEARCH");

    }
}
