package tests.utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.locators.Onboarding;

import static tests.locators.Generic.ENTER_SEARCH_TEXT;
import static tests.locators.Onboarding.*;
import static tests.utils.SetUp.driver;
import static tests.utils.SetUp.wait;

public class Generic {

    public static void setUp() {
        SetUp.setUp();
    }

    public static void completeOnboardingFlow() {
        // Onboarding flow
        wait.until(ExpectedConditions.presenceOfElementLocated(WELCOME_SCREEN_TEXT)).isDisplayed();
        WebElement continueBtn = driver.findElement(CONTINUE_BUTTON);
        continueBtn.click();
        // Policy Screen
        wait.until(ExpectedConditions.presenceOfElementLocated(ACCEPT_POLICY_BUTTON)).isDisplayed();
        WebElement acceptPolicyBtn = driver.findElement(ACCEPT_POLICY_BUTTON);
        acceptPolicyBtn.click();
        // Welcome screen
        wait.until(ExpectedConditions.presenceOfElementLocated(WELCOME_SCREEN_TEXT)).isDisplayed();
        continueBtn = driver.findElement(CONTINUE_BUTTON);
        continueBtn.click();
        // League selection
        wait.until(ExpectedConditions.presenceOfElementLocated(CHOOSE_LEAGUES_TEXT)).isDisplayed();
        continueBtn = driver.findElement(CONTINUE_BUTTON);
        continueBtn.click();
        // Location permission dialogue
        wait.until(ExpectedConditions.presenceOfElementLocated(LOCATION_PERMISSION_HEADER)).isDisplayed();
        WebElement denyLocationBtn = driver.findElement(DENY_LOCATION_BUTTON);
        denyLocationBtn.click();
        // Look up team and favorite
        wait.until(ExpectedConditions.presenceOfElementLocated(Onboarding.SEARCH_BAR)).isDisplayed();
        WebElement searchBar = driver.findElement(Onboarding.SEARCH_BAR);
        searchBar.click();
        searchBar = driver.findElement(ENTER_SEARCH_TEXT);
        String favTeam = "Milwaukee Bucks";
        searchBar.sendKeys(favTeam);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/txt_name' and @text='" + favTeam + "']"))).isDisplayed();
        WebElement searchResult = driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/txt_name' and @text='" + favTeam + "']"));
        searchResult.click();
        wait.until(ExpectedConditions.attributeToBe(continueBtn, "enabled", "true"));
        continueBtn = driver.findElement(CONTINUE_BUTTON);
        continueBtn.click();
        wait.until(ExpectedConditions.attributeToBe(continueBtn, "enabled", "true"));
        continueBtn = driver.findElement(CONTINUE_BUTTON);
        continueBtn.click();
        // Notifications permission dialogue
        wait.until(ExpectedConditions.presenceOfElementLocated(NOTIFICATIONS_PERMISSIONS_DIALOGUE)).isDisplayed();
        WebElement allowNotificationsBtn = driver.findElement(ALLOW_NOTIFICATIONS_BUTTON);
        allowNotificationsBtn.click();
    }

    public static void search(String name) {
        wait.until(ExpectedConditions.presenceOfElementLocated(tests.locators.Generic.SEARCH_BAR)).isDisplayed();
        WebElement searchBar = driver.findElement(tests.locators.Generic.SEARCH_BAR);
        searchBar.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(ENTER_SEARCH_TEXT)).isDisplayed();
        searchBar = driver.findElement(ENTER_SEARCH_TEXT);
        searchBar.sendKeys(name);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[contains(@text, '" + name + "')]"))).isDisplayed();
        WebElement searchResult = driver.findElement(By.xpath("//android.widget.TextView[contains(@text, '" + name + "')]"));
        searchResult.click();
    }

    public static void tearDown() {
        TearDown.tearDown();
    }
}
