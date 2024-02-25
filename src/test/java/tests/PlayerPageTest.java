package tests;

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
import static tests.utils.TestUtilities.openPlayerSubTab;
import static tests.utils.TestUtilities.verifyPlayerBirthdateAndAge;
import static tests.utils.TestUtilities.verifyPlayerBirthPlace;
import static tests.utils.TestUtilities.verifyBackNavigation;
import static tests.locators.PlayerPage.INFO_TAB;

@RunWith(Parameterized.class)
public class PlayerPageTest {

    private final String inputPlayerName;
    private final String expectedBirthdate;
    private final String expectedBirthPlace;

    public PlayerPageTest(String inputPlayerName, String expectedBirthdate, String expectedBirthPlace) {
        this.inputPlayerName = inputPlayerName;
        this.expectedBirthdate = expectedBirthdate;
        this.expectedBirthPlace = expectedBirthPlace;
    }

    @Parameters
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][] {
                {"Giannis Antetokounmpo", "1994-12-06", "Athens, GRC"},
                {"Stefanos Tsitsipas", "Aug 12, 1998", "Greece"},
                {"Tiger Woods", "December 30, 1975", "Cypress, California, United States"}
        });
    }

    @Test
    public void verifyInfoTab() {
        setUp();

        completeOnboardingFlow();
        search(inputPlayerName);
        verifyPlayerPageOpened();
        openPlayerSubTab(INFO_TAB);
        verifyPlayerBirthdateAndAge(expectedBirthdate);
        verifyPlayerBirthPlace(expectedBirthPlace);
        verifyBackNavigation(inputPlayerName);

        softAssert.assertAll();
        tearDown();
    }

}
