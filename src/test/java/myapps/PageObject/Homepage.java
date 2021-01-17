package myapps.PageObject;
import id.aldochristiaan.salad.module.Espresso;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;


public class Homepage extends Espresso {
    public Homepage(AndroidDriver<AndroidElement> androidDriver) {

        super(androidDriver);
    }

    public void validateHomepage(){

        validateDisplayed("ICONSEARCH", 15);
    }

    public void typeSearch(String name){
        tap().element("TEXT_SEARCH");
        type().element("TEXT_SEARCH", name);
    }


}
