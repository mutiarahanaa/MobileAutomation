package myapps.PageObject;

import id.aldochristiaan.salad.module.Espresso;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Basepage extends Espresso {

    public Basepage(AndroidDriver<AndroidElement> androidDriver) {
        super(androidDriver);
    }

    public Homepage homepage() {
        return new Homepage(androidDriver);
    }


}
