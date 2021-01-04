package myapps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import myapps.PageObject.Homepage;

public class MyApps {
    private AndroidDriver<AndroidElement> androidDriver;

    public MyApps(AndroidDriver<AndroidElement> androidDriver) {
        this.androidDriver = androidDriver;
    }

    public Homepage homePage() {

        return new Homepage(androidDriver);
    }
}
