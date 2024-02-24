package tests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import static tests.utils.TestUtilities.softAssert;
import static tests.utils.TestUtilities.setUp;
import static tests.utils.TestUtilities.tearDown;
import static tests.utils.TestUtilities.completeOnboardingFlow;
import static tests.utils.TestUtilities.search;
import static tests.utils.TestUtilities.verifyPlayerPageOpened;
import static tests.utils.TestUtilities.openPlayerInfoTab;
import static tests.utils.TestUtilities.verifyPlayerBirthDateAndAge;
import static tests.utils.TestUtilities.verifyNavigateUp;


public class PlayerPageTest {

    // Parameters(String name, Date birthDate, String birthPlace) to be added
    @Test
    public void verifyInfoTab() {
        setUp();

        completeOnboardingFlow();
        search("Giannis Antetokounmpo");
        verifyPlayerPageOpened("Giannis Antetokounmpo");
        openPlayerInfoTab();

        DateTimeFormatter birthDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate expectedBirthDate = LocalDate.parse("1994-12-06", birthDateFormatter);

        verifyPlayerBirthDateAndAge(expectedBirthDate);
        verifyNavigateUp();

        // TODO: Make sure this assert all works
        softAssert.assertAll();

        tearDown();
    }

}
