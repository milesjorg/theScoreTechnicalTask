package tests.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.configuration.AndroidDriverConfig;

import java.time.Duration;
import java.util.List;

public class BasePage {
    protected static AndroidDriver driver;
    protected static WebDriverWait wait;
    protected static SoftAssertions softAssert;

    public BasePage(AndroidDriver driver) {
        BasePage.driver = AndroidDriverConfig.initializeAndroidDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        softAssert = new SoftAssertions();
    }

    protected static void waitForElementPresent(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected static void waitForAllElementsPresent(By locator) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    protected static WebElement findElement(By locator) {
        waitForElementPresent(locator);
        return driver.findElement(locator);
    }

    protected static List<WebElement> findElements(By locator) {
        waitForElementPresent(locator);
        return driver.findElements(locator);
    }

    protected static void click(By locator) {
        findElement(locator).click();
    }

    protected static void waitForElementAttribute(WebElement element, String attribute, String value) {
        wait.until(ExpectedConditions.attributeToBe(element, attribute, value));
    }

    protected static void assertElementDisplayed(By locator) {
        WebElement element = findElement(locator);
        softAssert.assertThat(element.isDisplayed()).isTrue();
    }

    protected static void assertElementAttribute(WebElement element, String attribute, String value) {
        softAssert.assertThat(element.getAttribute(attribute)).isEqualTo(value);
    }

    protected static <T> void assertEqual(T actual, T expected) {
        softAssert.assertThat(actual).isEqualTo(expected);
    }

}
