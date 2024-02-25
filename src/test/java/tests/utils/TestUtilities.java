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
import java.util.List;
import java.util.Locale;

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

    public static void verifyPlayerPageOpened() {
        wait.until(ExpectedConditions.presenceOfElementLocated(HEADER)).isDisplayed();
        softAssert.assertThat(driver.findElement(HEADER).isDisplayed()).isTrue();
    }

    public static void openPlayerSubTab(By subTab) {
        wait.until(ExpectedConditions.presenceOfElementLocated(subTab));
        WebElement selectedSubTab = driver.findElement(subTab);
        selectedSubTab.click();
        softAssert.assertThat(selectedSubTab.getAttribute("selected")).isEqualTo("true");
    }

    // TODO: Identify the format the birthdateAndAge string is in, then split age and birthday accordingly
    public static void verifyPlayerBirthdateAndAge(String expectedBirthdateStr) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(INFO_TAB_PLAYER_DETAILS));
        List<WebElement> playerDetails = driver.findElements(INFO_TAB_PLAYER_DETAILS);
        String displayedBirthdateAndAge = playerDetails.get(0).getAttribute("text");
        String dateFirstPattern = "\\d{4}-\\d{2}-\\d{2} \\(Age \\d+\\)";
        String ageFirstPattern = "\\d{2} \\(\\p{Lu}[a-zA-Z]+ \\d{1,2}, \\d{4}\\)";
        String monthFirstPattern = "\\p{Lu}[a-zA-Z]+ \\d{1,2}, \\d{4} \\(\\d+\\)";

        String dateSubstring;
        String ageSubstring;
        LocalDate displayedBirthdate;
        LocalDate expectedBirthdate;

        if (displayedBirthdateAndAge.matches(dateFirstPattern)) {
            dateSubstring = displayedBirthdateAndAge.substring(0, displayedBirthdateAndAge.indexOf(" ("));
            ageSubstring = displayedBirthdateAndAge.substring(displayedBirthdateAndAge.indexOf("Age ") + 4, displayedBirthdateAndAge.indexOf(")"));
            DateTimeFormatter birthdateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            displayedBirthdate = LocalDate.parse(dateSubstring, birthdateFormatter);
            expectedBirthdate = LocalDate.parse(expectedBirthdateStr, birthdateFormatter);

        } else if (displayedBirthdateAndAge.matches(ageFirstPattern)) {
            dateSubstring = displayedBirthdateAndAge.substring(displayedBirthdateAndAge.indexOf(" (") + 2, displayedBirthdateAndAge.length() - 1);
            ageSubstring = displayedBirthdateAndAge.substring(0, displayedBirthdateAndAge.indexOf(" ("));
            DateTimeFormatter birthdateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy", Locale.ENGLISH);
            displayedBirthdate = LocalDate.parse(dateSubstring, birthdateFormatter);
            expectedBirthdate = LocalDate.parse(expectedBirthdateStr, birthdateFormatter);
        } else {
            dateSubstring = displayedBirthdateAndAge.substring(0, displayedBirthdateAndAge.indexOf(" ("));
            ageSubstring = displayedBirthdateAndAge.substring(displayedBirthdateAndAge.indexOf(" (") + 2, displayedBirthdateAndAge.length() - 1);
            DateTimeFormatter birthdateFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH);
            displayedBirthdate = LocalDate.parse(dateSubstring, birthdateFormatter);
            expectedBirthdate = LocalDate.parse(expectedBirthdateStr, birthdateFormatter);
        }

        int displayedAge = Integer.parseInt(ageSubstring);
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(displayedBirthdate, currentDate);
        int expectedAge = period.getYears();
        softAssert.assertThat(displayedAge).isEqualTo(expectedAge);
        softAssert.assertThat(displayedBirthdate).isEqualTo(expectedBirthdate);
    }

    public static void verifyPlayerBirthPlace(String expectedBirthPlace) {
        wait.until(ExpectedConditions.presenceOfElementLocated(INFO_TAB_PLAYER_DETAILS));
        softAssert.assertThat(driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/value' and @text='"+expectedBirthPlace+"']")).isDisplayed()).isTrue();
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
