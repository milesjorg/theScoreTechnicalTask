package tests.utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static tests.locators.Generic.NAVIGATION_BACK_BUTTON;
import static tests.locators.PlayerPage.HEADER;
import static tests.locators.PlayerPage.INFO_TAB_PLAYER_DETAILS;
import static tests.utils.SetUp.*;

public class PlayerPage {

    @BeforeClass
    public static void setUp() {
        SetUp.setUp();
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
        WebElement displayedBirthPlace = driver.findElement(By.xpath("//android.widget.TextView[@resource-id='com.fivemobile.thescore:id/value' and @text='" + expectedBirthPlace + "']"));
        softAssert.assertThat(displayedBirthPlace.isDisplayed()).isTrue();
    }

    @AfterClass
    public static void tearDown() {
        TearDown.tearDown();
    }
}
