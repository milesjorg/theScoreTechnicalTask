package tests.utils;

import io.appium.java_client.android.AndroidDriver;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.configuration.AndroidDriverConfig;
import tests.locators.Generic;
import tests.locators.Onboarding;

import static tests.locators.Onboarding.*;
import static tests.locators.Generic.*;
import static tests.locators.PlayerPage.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class TestUtilities {

    private static AndroidDriver driver;
    private static WebDriverWait wait;
    public static SoftAssertions softAssert;

    public static void setUp() {
        driver = AndroidDriverConfig.initializeAndroidDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        softAssert = new SoftAssertions();
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
        wait.until(ExpectedConditions.presenceOfElementLocated(Generic.SEARCH_BAR)).isDisplayed();
        WebElement searchBar = driver.findElement(Generic.SEARCH_BAR);
        searchBar.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(ENTER_SEARCH_TEXT)).isDisplayed();
        searchBar = driver.findElement(ENTER_SEARCH_TEXT);
        searchBar.sendKeys(name);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[contains(@text, '" + name + "')]"))).isDisplayed();
        WebElement searchResult = driver.findElement(By.xpath("//android.widget.TextView[contains(@text, '" + name + "')]"));
        searchResult.click();
    }

    public static void verifyBackNavigation(String name) {
        WebElement navigateUpBtn = driver.findElement(NAVIGATION_BACK_BUTTON);
        navigateUpBtn.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[contains(@text, '" + name + "')]"))).isDisplayed();
        softAssert.assertThat(driver.findElement(By.xpath("//android.widget.TextView[contains(@text, '" + name + "')]")).isDisplayed()).isTrue();
    }

    public static void verifyPlayerPageOpened(String name) {
        wait.until(ExpectedConditions.presenceOfElementLocated(HEADER)).isDisplayed();
        String playerName = driver.findElement(PLAYER_NAME_TEXT).getAttribute("text");
        softAssert.assertThat(name).isEqualTo(playerName);
    }

    public static void openPlayerSubTab(By subTab) {
        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        WebElement selectedSubTab = driver.findElement(subTab);
        selectedSubTab.click();
        softAssert.assertThat(selectedSubTab.getAttribute("selected")).isEqualTo("true");
    }

    // TODO: Make adaptive to different players
    public static void verifyPlayerBirthDateAndAge(LocalDate expectedBirthDate) {
        wait.until(ExpectedConditions.presenceOfElementLocated(BIRTH_DATE_HEADER));
        String displayedBirthDateAndAge = driver.findElement(AGE_TEXT).getAttribute("text");
        DateTimeFormatter birthDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate displayedBirthDate = LocalDate.parse(displayedBirthDateAndAge.substring(0, displayedBirthDateAndAge.indexOf(" (")), birthDateFormatter);
        softAssert.assertThat(displayedBirthDate).isEqualTo(expectedBirthDate);
        int displayedAge = Integer.parseInt(displayedBirthDateAndAge.substring(displayedBirthDateAndAge.indexOf("Age ") + 4, displayedBirthDateAndAge.indexOf(")")).trim());
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(displayedBirthDate, currentDate);
        int expectedAge = period.getYears();
        softAssert.assertThat(displayedAge).isEqualTo(expectedAge);
    }

    public static void verifyPlayerBirthPlace(String expectedBirthPlace) {
        wait.until(ExpectedConditions.presenceOfElementLocated(BIRTH_PLACE_HEADER));
        softAssert.assertThat(driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/value' and @text='"+expectedBirthPlace+"']")).isDisplayed()).isTrue();
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
