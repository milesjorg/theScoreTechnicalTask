package tests.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.configuration.AndroidDriverConfig;

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
        //Onboarding flow
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.fivemobile.thescore:id/txt_welcome"))).isDisplayed();
        WebElement continueBtn = driver.findElement(By.id("com.fivemobile.thescore:id/btn_primary"));
        continueBtn.click();
        // Policy Screen
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.fivemobile.thescore:id/accept_button"))).isDisplayed();
        WebElement acceptPolicyBtn = driver.findElement(By.id("com.fivemobile.thescore:id/accept_button"));
        acceptPolicyBtn.click();
        // Welcome screen
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.fivemobile.thescore:id/txt_welcome"))).isDisplayed();
        continueBtn = driver.findElement(By.id("com.fivemobile.thescore:id/btn_primary"));
        continueBtn.click();
        // League selection
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.fivemobile.thescore:id/title_onboarding"))).isDisplayed();
        continueBtn = driver.findElement(By.id("com.fivemobile.thescore:id/btn_primary"));
        continueBtn.click();
        // Location permission dialogue
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.fivemobile.thescore:id/location_title"))).isDisplayed();
        WebElement denyLocationBtn = driver.findElement(By.id("com.fivemobile.thescore:id/btn_disallow"));
        denyLocationBtn.click();
        // Look up team and favorite
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.fivemobile.thescore:id/search_bar_placeholder"))).isDisplayed();
        WebElement searchBar = driver.findElement(By.id("com.fivemobile.thescore:id/search_bar_placeholder"));
        searchBar.click();
        searchBar = driver.findElement(By.id("com.fivemobile.thescore:id/search_src_text"));
        searchBar.sendKeys("Milwaukee Bucks");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/txt_name' and @text='Milwaukee Bucks']"))).isDisplayed();
        WebElement searchResult = driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/txt_name' and @text='Milwaukee Bucks']"));
        searchResult.click();
        wait.until(ExpectedConditions.attributeToBe(continueBtn, "enabled", "true"));
        continueBtn = driver.findElement(By.id("com.fivemobile.thescore:id/btn_primary"));
        continueBtn.click();
        wait.until(ExpectedConditions.attributeToBe(continueBtn, "enabled", "true"));
        continueBtn = driver.findElement(By.id("com.fivemobile.thescore:id/btn_primary"));
        continueBtn.click();
        // Notifications permission dialogue
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.android.permissioncontroller:id/permission_message"))).isDisplayed();
        WebElement allowNotificationsBtn = driver.findElement(By.id("com.android.permissioncontroller:id/permission_allow_button"));
        allowNotificationsBtn.click();
    }

    public static void search(String name) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.fivemobile.thescore:id/search_bar_text_view"))).isDisplayed();
        WebElement searchBar = driver.findElement(By.id("com.fivemobile.thescore:id/search_bar_text_view"));
        searchBar.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.fivemobile.thescore:id/search_src_text"))).isDisplayed();
        searchBar = driver.findElement(By.id("com.fivemobile.thescore:id/search_src_text"));
        searchBar.sendKeys(name);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[contains(@text, '" + name + "')]"))).isDisplayed();
        WebElement searchResult = driver.findElement(By.xpath("//android.widget.TextView[contains(@text, '" + name + "')]"));
        searchResult.click();
    }

    public static void verifyNavigateUp() {
        WebElement navigateUpBtn = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"));
        navigateUpBtn.click();
        // TODO: Verify this in a more resilient way
        softAssert.assertThat(driver.findElement(By.id("com.fivemobile.thescore:id/search_src_text")).isDisplayed()).isTrue();
    }

    public static void verifyPlayerPageOpened(String name) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.fivemobile.thescore:id/player_header"))).isDisplayed();
        String playerName = driver.findElement(By.id("com.fivemobile.thescore:id/txt_player_name")).getAttribute("text");
        softAssert.assertThat(name).isEqualTo(playerName);
    }

    public static void openPlayerInfoTab() {
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("Info")));
        WebElement infoTab = driver.findElement(AppiumBy.accessibilityId("Info"));
        infoTab.click();
        softAssert.assertThat(infoTab.getAttribute("selected")).isEqualTo("true");
    }

    public static void verifyPlayerBirthDateAndAge(LocalDate expectedBirthDate) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/title' and @text='Birth Date']")));
        String displayedBirthDateAndAge = driver.findElement(By.xpath("//android.widget.TextView[contains(@text, 'Age')]")).getAttribute("text");
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
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/title' and @text='Birth Place']")));
        softAssert.assertThat(driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/value' and @text='"+expectedBirthPlace+"']")).isDisplayed()).isTrue();
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
