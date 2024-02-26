package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import tests.utils.SetUp;
import tests.utils.TearDown;

import java.util.Arrays;
import java.util.Collection;

import static tests.locators.PlayerPage.INFO_TAB;
import static tests.utils.Generic.completeOnboardingFlow;
import static tests.utils.Generic.search;
import static tests.utils.PlayerPage.*;
import static tests.utils.SetUp.softAssert;

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
        return Arrays.asList(new Object[][]{
                {"Giannis Antetokounmpo", "1994-12-06", "Athens, GRC"},
                {"Stefanos Tsitsipas", "Aug 12, 1998", "Greece"},
                {"Tiger Woods", "December 30, 1975", "Cypress, California, United States"}
        });
    }

    @Test
    public void verifyInfoTab() {

        SetUp.setUp();
        completeOnboardingFlow();
        search(inputPlayerName);
        verifyPlayerPageOpened();
        openPlayerSubTab(INFO_TAB);
        verifyPlayerBirthdateAndAge(expectedBirthdate);
        verifyPlayerBirthPlace(expectedBirthPlace);
        verifyBackNavigation(inputPlayerName);

        softAssert.assertAll();
        TearDown.tearDown();
    }
}
