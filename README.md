# theScore QA Mobile Automation Technical Task

## Introduction
This project automates a test in theScore mobile app for android devices. The test consists of opening the app as a first time user, completing the onboarding flow, searching for a player, opening their player page, and verifying that player info is correctly displayed in the info tab of the player page, as well as verifying the back navigation function. It uses a gradle build file using repository dependencies with Java 11 and Appium 2 as the test host. The aim of this project is to serve as a foundation for an automation suite, where future tests can be added to increase test covereage of the app.
## Prerequisites
This project assumes the following tools are already installed on your machine:
- Java JDK 11
- Appium2
- Android Virtual Device Emulator (Android Studio)
- Git
## Setup
1. Open a new project in your IDE and clone the repository from the terminal
```
git clone https://github.com/milesjorg/theScoreTechnicalTask.git
```
2. Start running your android emulator device and ensure the startup is complete and the virtual device is ready
3. In a separate terminal, start the appium server
```
appium
```
## Running Tests
1. Ensure you are in the root directory of the project. Ex:
```
cd ~/IdeaProjects/Test/theScoreTechnicalTask
```
2. On MacOS or linux/unix systems, you may need to run the following command to allow permissions for your device to run the `gradlew` build script
```
chmod +x gradlew
```
3. Run the gradle build command. This will ensure the correct gradle version is being used along with the project dependencies and run the test

#### MacOS/Linux/Unix
```
./gradlew build
```
#### Windows
```
./gradlew.bat build
```
4. You can rerun the tests using the command
#### MacOS/Linux/Unix
```
./gradlew test --tests PlayerPageTest
```
#### Windows
```
./gradlew test --tests PlayerPageTest
```

## Test Specification

### Test Scenario
A first time user opens the app, completes the onboarding flow, searches for a player to see the info sub-tab on the player page, and navigates back to the search results using the back button.

### Test Data
- "Giannis Antetokounmpo", "1994-12-06", "Athens, GRC"
- "Stefanos Tsitsipas", "Aug 12, 1998", "Greece"
- "Tiger Woods", "December 30, 1975", "Cypress, California, United States"
  
### Expected Results
- Verify onboarding flow for first time user
- Verify birthdate and age for each player
- Verify birthplace for each player
- Verify back button takes the user from the player page back to the search results

### Test Steps:
1. Complete onboarding flow for first time user
2. Search for player from Home page
3. Check that the player page opens upon selecting the search result
4. Open the info sub-tab on player page
5. Check the displayed birthdate and age and compare to the test data
6. Check the displayed birthplace and compare to the test data
7. Tap the back button to open search results

## Rationale for Test Approach:
I chose this testing strategy because it tests critical app functionalities:

#### First-Time User Experience
Tests onboarding for new users, covering policy screens, team preferences, and permissions.

#### Search Functionality
Validates basic search functionality for players across different sports, adding coverage for different formatting of player info.

#### Player Page Validation
Confirms birthdate and birthplace accuracy, leveraging static data for stable tests.

#### Back Navigation Functionality
Validates basic back navigation functionality from the player page to the search results.

### Soft Assertions
Enhances test reporting by capturing multiple failures in a single run.

## Coverage Assessment:

#### First-Time User Experience
✅ Successfully verifies onboarding screens, policy acceptance, and location/team preference setup.

⚠️ Improvement opportunity: Limited coverage of scenarios with denied permissions.

#### Search Functionality
✅ Validates search functionality for common player names.

⚠️ Improvement opportunity: Test edge cases and potential issues with uncommon or misspelled names.

#### Player Page Validation
✅ Confirms accurate display of player birthdate and birthplace in various formats.

⚠️ Improvement opportunity: Extend coverage to handle dynamic scenarios with changing data.

#### Back Navigation Functionality
✅ Validates basic back navigation functionality from the player page to the search results.

⚠️ Improvement opportunity: Test back navigation functionality from other screens in the app.

#### Soft Assertions
✅ Provides robust reporting by capturing multiple failures in a single test run.

⚠️ Improvement opportunity: Enhance failure messages for improved debugging.
