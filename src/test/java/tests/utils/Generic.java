package tests.utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import tests.locators.Onboarding;

import static tests.locators.Generic.ENTER_SEARCH_TEXT;
import static tests.locators.Onboarding.*;
import static tests.utils.SetUp.driver;
import static tests.utils.SetUp.wait;

public class Generic {

    public static void setUp() {
        SetUp.setUp();
    }

    public static void tearDown() {
        TearDown.tearDown();
    }
}
