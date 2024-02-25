package tests.locators;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class PlayerPage {
    public static By HEADER = By.id("com.fivemobile.thescore:id/player_header");
    public static By PLAYER_NAME_TEXT = By.id("com.fivemobile.thescore:id/txt_player_name");
    public static By INFO_TAB = AppiumBy.accessibilityId("Info");
    public static By INFO_TAB_PLAYER_DETAILS = By.id("com.fivemobile.thescore:id/value");
    public static By BIRTH_DATE_HEADER = By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/title' and @text='Birth Date']");
    public static By AGE_TEXT = By.xpath("//android.widget.TextView[contains(@text, 'Age')]");
    public static By BIRTH_PLACE_HEADER = By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/title' and @text='Birth Place']");

}
