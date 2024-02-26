package tests;

import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import tests.configuration.AndroidDriverConfig;
import tests.pageobjects.GenericPage;
import tests.pageobjects.OnboardingFlowPage;
import tests.pageobjects.PlayerPage;

import java.util.Arrays;
import java.util.Collection;

import static tests.locators.PlayerPage.INFO_TAB;
import static tests.utils.SetUp.softAssert;

@RunWith(Parameterized.class)
public class PlayerPageTest {

    private AndroidDriver driver;
    private OnboardingFlowPage onboardingFlowPage;
    private GenericPage genericPage;
    private PlayerPage playerPage;

    private final String inputPlayerName;
    private final String expectedBirthdate;
    private final String expectedBirthPlace;

    public PlayerPageTest(String inputPlayerName, String expectedBirthdate, String expectedBirthPlace) {
        this.inputPlayerName = inputPlayerName;
        this.expectedBirthdate = expectedBirthdate;
        this.expectedBirthPlace = expectedBirthPlace;
    }

    @Before
    public void setUp() {
        driver = AndroidDriverConfig.initializeAndroidDriver();
        onboardingFlowPage = new OnboardingFlowPage(driver);
        genericPage = new GenericPage(driver);
        playerPage = new PlayerPage(driver);
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

        onboardingFlowPage.completeOnboardingFlow();

        genericPage.search(inputPlayerName);

        playerPage.verifyPlayerPageOpened();
        playerPage.openPlayerSubTab(INFO_TAB);
        playerPage.verifyPlayerBirthdateAndAge(expectedBirthdate);
        playerPage.verifyPlayerBirthPlace(expectedBirthPlace);
        playerPage.verifyBackNavigation(inputPlayerName);

        softAssert.assertAll();

    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
