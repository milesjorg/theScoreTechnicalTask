package tests.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static tests.locators.Generic.ENTER_SEARCH_TEXT;
import static tests.locators.Onboarding.*;
import static tests.locators.Onboarding.ALLOW_NOTIFICATIONS_BUTTON;

public class OnboardingFlowPage extends BasePage {

    public OnboardingFlowPage(AndroidDriver driver) {
        super(driver);
    }

    public static void completeOnboardingFlow() {
        // Welcome screen
        click(CONTINUE_BUTTON);
        // Policy screen
        click(ACCEPT_POLICY_BUTTON);
        // Welcome screen
        click(CONTINUE_BUTTON);
        // League selection
        click(CONTINUE_BUTTON);
        // Location permission dialogue
        waitForElementPresent(LOCATION_PERMISSION_HEADER);
        click(DENY_LOCATION_BUTTON);
        // Look up team and favorite
        click(SEARCH_BAR);
        WebElement searchBar = findElement(ENTER_SEARCH_TEXT);
        click(ENTER_SEARCH_TEXT);
        String favTeam = "Milwaukee Bucks";
        searchBar.sendKeys(favTeam);
        waitForElementPresent(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/txt_name' and @text='" + favTeam + "']"));;
        click(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/txt_name' and @text='" + favTeam + "']"));
        WebElement continueBtn = findElement(CONTINUE_BUTTON);
        waitForElementAttribute(continueBtn, "enabled", "true");
        click(CONTINUE_BUTTON);
        // Notifications permission dialogue
        waitForElementPresent(NOTIFICATIONS_PERMISSIONS_DIALOGUE);
        click(ALLOW_NOTIFICATIONS_BUTTON);
    }
}
