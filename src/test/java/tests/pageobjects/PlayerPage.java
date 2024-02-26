package tests.pageobjects;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static tests.locators.Generic.NAVIGATION_BACK_BUTTON;
import static tests.locators.PlayerPage.HEADER;
import static tests.locators.PlayerPage.INFO_TAB_PLAYER_DETAILS;

public class PlayerPage extends BasePage{
    public PlayerPage(AndroidDriver driver) {
        super(driver);
    }

    public static void verifyPlayerPageOpened() {
        waitForElementPresent(HEADER);
        assertElementDisplayed(HEADER);
    }

    public static void openPlayerSubTab(By subTab) {
        waitForElementPresent(subTab);
        click(subTab);
        WebElement subTabElement = findElement(subTab);
        assertElementAttribute(subTabElement, "selected", "true");
    }

    public static void verifyPlayerBirthdateAndAge(String expectedBirthdateStr) {
        waitForAllElementsPresent(INFO_TAB_PLAYER_DETAILS);
        List<WebElement> playerDetails = findElements(INFO_TAB_PLAYER_DETAILS);
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
        assertEqual(displayedAge, expectedAge);
        assertEqual(displayedBirthdate, expectedBirthdate);
    }

    public static void verifyPlayerBirthPlace(String expectedBirthPlace) {
        waitForElementPresent(INFO_TAB_PLAYER_DETAILS);
        assertElementDisplayed(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/value' and @text='" + expectedBirthPlace + "']"));
    }

    public static void verifyBackNavigation(String name) {
        click(NAVIGATION_BACK_BUTTON);
        waitForElementPresent(By.xpath("//android.widget.TextView[contains(@text, '" + name + "')]"));
        assertElementDisplayed(By.xpath("//android.widget.TextView[contains(@text, '" + name + "')]"));
    }
}
