package myapps;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import myapps.PageObject.Homepage;

public class myapps {
    private AndroidDriver<AndroidElement> androidDriver;

    public myapps(AndroidDriver<AndroidElement> androidDriver) {
        this.androidDriver = androidDriver;
    }

    public Homepage homePO() {
        return new Homepage(androidDriver);
    }
}
