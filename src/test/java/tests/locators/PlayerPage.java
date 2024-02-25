package tests.locators;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class PlayerPage {
    public static By HEADER = By.id("com.fivemobile.thescore:id/player_header");
    public static By INFO_TAB = AppiumBy.accessibilityId("Info");
    public static By INFO_TAB_PLAYER_DETAILS = By.id("com.fivemobile.thescore:id/value");

}
