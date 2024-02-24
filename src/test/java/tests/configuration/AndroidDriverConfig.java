package tests.configuration;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;

import java.net.MalformedURLException;
import java.net.URL;

public class AndroidDriverConfig {

    public static AndroidDriver initializeAndroidDriver() {
        AndroidDriver driver;
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setDeviceName("test-device");
        options.setApp(System.getProperty("user.dir") + "/apps/theScore_ Sports News & Scores_24.2.0_Apkpure.apk");

        try {
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to initialize Android driver: " + e.getMessage());
        }

        return driver;
    }

}
