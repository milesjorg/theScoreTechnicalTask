package tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static tests.utils.TestUtilities.softAssert;
import static tests.utils.TestUtilities.setUp;
import static tests.utils.TestUtilities.tearDown;
import static tests.utils.TestUtilities.completeOnboardingFlow;
import static tests.utils.TestUtilities.search;
import static tests.utils.TestUtilities.verifyPlayerPageOpened;
import static tests.utils.TestUtilities.openPlayerInfoTab;
import static tests.utils.TestUtilities.verifyPlayerBirthDateAndAge;
import static tests.utils.TestUtilities.verifyPlayerBirthPlace;
import static tests.utils.TestUtilities.verifyNavigateUp;

@RunWith(Parameterized.class)
public class PlayerPageTest {

    private final String inputPlayerName;
    private final LocalDate expectedBirthDate;
    private final String expectedBirthPlace;

    public PlayerPageTest(String inputPlayerName, String expectedBirthDate, String expectedBirthPlace) {
        this.inputPlayerName = inputPlayerName;
        this.expectedBirthDate = LocalDate.parse(expectedBirthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.expectedBirthPlace = expectedBirthPlace;
    }

    @Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][] {
                {"Giannis Antetokounmpo", "1994-12-06", "Athens, GRC"},
                {"Khris Middleton", "1991-08-12", "Charleston, SC"},
                {"Damian Lillard", "1990-07-15", "Oakland, CA"}
        });
    }

    @Test
    public void verifyInfoTab() {
        setUp();

        completeOnboardingFlow();
        search(inputPlayerName);
        verifyPlayerPageOpened(inputPlayerName);
        openPlayerInfoTab();
        verifyPlayerBirthDateAndAge(expectedBirthDate);
        verifyPlayerBirthPlace(expectedBirthPlace);
        verifyNavigateUp();

        softAssert.assertAll();
        tearDown();
    }

}
