package tests.utils;

import io.appium.java_client.android.AndroidDriver;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.configuration.AndroidDriverConfig;

import java.time.Duration;

public class SetUp {
    private static boolean isDriverInitialized = false;
    public static AndroidDriver driver;
    public static WebDriverWait wait;
    public static SoftAssertions softAssert;

    public static void setUp() {
        if (!isDriverInitialized) {
            driver = AndroidDriverConfig.initializeAndroidDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            softAssert = new SoftAssertions();
            isDriverInitialized = true;
        }
    }
}
