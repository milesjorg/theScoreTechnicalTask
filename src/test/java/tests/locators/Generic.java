package tests.locators;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class Generic {
    public static By SEARCH_BAR = By.id("com.fivemobile.thescore:id/search_bar_text_view");
    public static By ENTER_SEARCH_TEXT = By.id("com.fivemobile.thescore:id/search_src_text");
    public static By NAVIGATION_BACK_BUTTON = AppiumBy.accessibilityId("Navigate up");

}
