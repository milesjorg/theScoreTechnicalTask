package tests.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static tests.locators.Generic.SEARCH_BAR;

public class GenericPage extends BasePage {
    public GenericPage(AndroidDriver driver) {
        super(driver);
    }

    public static void search(String name) {
        waitForElementPresent(SEARCH_BAR);
        click(SEARCH_BAR);
        WebElement searchBar = findElement(SEARCH_BAR);
        searchBar.sendKeys(name);
        click(By.xpath("//android.widget.TextView[contains(@text, '" + name + "')]"));
    }
}
