package tests.utils;

import static tests.utils.SetUp.driver;

public class TearDown {
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
