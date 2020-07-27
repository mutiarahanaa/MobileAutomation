import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import myapps.PageObject.HomepagePO;

public class MyApps {
    private AndroidDriver<AndroidElement> androidDriver;

    public MyApps(AndroidDriver<AndroidElement> androidDriver) {
        this.androidDriver = androidDriver;
    }

    public HomepagePO homepagePO() {
        return new HomepagePO(androidDriver);
    }
}
