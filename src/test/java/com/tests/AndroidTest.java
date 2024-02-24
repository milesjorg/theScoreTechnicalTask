package com.test;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.junit.Test;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AndroidTest {

    @Test
    public void androidLaunchTest() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2);
        options.setDeviceName("test-device");
        options.setApp(System.getProperty("user.dir") + "/apps/theScore_ Sports News & Scores_24.2.0_Apkpure.apk");

        AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        SoftAssertions softAssert = new SoftAssertions();

        // Onboarding Flow
        // Welcome screen
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

        // Start of test
        /*
        Automate the following steps:

        1.Open a league, team, or player page of your choice (bonus points for
        using a data-driven or parameterized approach).

        2.Verify that the expected page opens correctly.
                Tap on a sub-tab of your choice, eg: league table / standings / leaders, or
        stats tab of the league, team, or player.
        3.

        Verify that you are on the correct tab and that the data is displayed
        correctly and corresponds to the league, team, or player from step 1.
        4.

        5.Verify that back navigation returns you to the previous page correctly.

        */
        // Look up team by name using search bar
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.fivemobile.thescore:id/search_bar_text_view"))).isDisplayed();
        searchBar = driver.findElement(By.id("com.fivemobile.thescore:id/search_bar_text_view"));
        searchBar.click();
        searchBar = driver.findElement(By.id("com.fivemobile.thescore:id/search_src_text"));
        // TODO: Create parameterized approach. Use an input data file?
        searchBar.sendKeys("Milwaukee Bucks");
        searchResult = driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/txt_name' and @text='Milwaukee Bucks']"));
        searchResult.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.fivemobile.thescore:id/team_name"))).isDisplayed();
        String teamName = driver.findElement(By.id("com.fivemobile.thescore:id/team_name")).getAttribute("text");
        // Verify that team names are equal
        softAssert.assertThat("Milwaukee Bucks").isEqualTo(teamName);

        // Go to stats sub-tab
        WebElement statsTab = driver.findElement(By.xpath("//android.widget.LinearLayout[@content-desc='Team Stats']"));
        statsTab.click();
        // Verify stats tab was selected
        statsTab = driver.findElement(By.xpath("//android.widget.LinearLayout[@content-desc='Team Stats']"));
        softAssert.assertThat(statsTab.getAttribute("selected")).isEqualTo("true");
        // TODO: How can I verify that the data corresponds to the selected team? Just check the team name again?
        teamName = driver.findElement(By.id("com.fivemobile.thescore:id/team_name")).getAttribute("text");
        // Verify that team names are equal
        softAssert.assertThat("Milwaukee Bucks").isEqualTo(teamName);



        softAssert.assertAll();
    }
}
