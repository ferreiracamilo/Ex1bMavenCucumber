#Author: Camilo Ferreira
Feature: App > Service App > Accounts
  It will be tested all features related to this functionality

  Background: Account functionality background
    Given Initialize the browser with "chrome"
    And User navigates to "https://login.salesforce.com" Site
    And User enters credentials and logs in
    And User clicks on waffle menu button
    And User clicks on Service button
    And User clicks on Accounts tab

  #Create account record by filling all fields (values will be provided within java class).
  #Additional requirement: value for SLA Expiration Date must be clicked in calendar.
  #Will be erase every account before running 1st scenario
  #Exercise 1b - 2
  Scenario: Create Account with full fields
    Given User erase all respective records
    And User clicks on New button
    And User inputs "1stAccount" as AccountName and fill rest of the account form
    And User Clicks on Save button
    When System launchs sucess notification
    Then Close browser

  #Verify error launches when Account Record is attemp to be created without filling mandatory field (Account Name)
  #Exercise 1b - 3
  Scenario: Negative outcome for account creation record due to void accountname field
    Given User clicks on New button
    And User Clicks on Save button
    When System launchs error stop sign icon due to not filling mandatory fields
    Then Close browser

  #Fields to modified are Rating, Upsell Opportunity, Type
  #CHECK IF WORK WITH REPEATED STEP IN LINE 43
  #Exercise 1b - 5
  Scenario: Modify values of Rating, Upsell Opportunity and Other from account record
    Given User click menu (arrow down) and click Edit button from row which contains "1stAccount"
    And User modify Rating to "Cold", Upsell Opportunity to "Yes" and Type to "Other" dropdown options
    And User Clicks on Save button
    When System launchs sucess notification
    And User click menu (arrow down) and click Edit button from row which contains "1stAccount"
    And User verifies dropdown values: Rating is "Cold", Upsell Opportunity is "Yes" and Type is "Other" on "1stAccount"
    Then Close browser

  #Field to modified is Employee
  #Exercise 1b - 6
  Scenario: Negative outcome for account edition record due to not admitted value in Employees
    Given User click menu (arrow down) and click Edit button from row which contains "1stAccount"
    And User modify Employees input field to "1431655766"
    And User Clicks on Save button
    When warning message "Employees: value outside of valid range on numeric field: 1431655766" is displayed
    Then Close browser

  #Exercise did not specify to fill the complete form, therefore dataprovider will contain only few possible fields
  #Exercise 1b - 7
  Scenario Outline: Create multiple account with DataProvider
    Given User clicks on New button
    And User inputs <accountName> into Account Name input, <phoneNumber> into Phone input, <websiteName> into Website input and <tyckerSymbol> into Tycker Symbol input
    And User Clicks on Save button
    When System launchs sucess notification
    Then Close browser

    Examples:
      | accountName   | phoneNumber | websiteName                    | tyckerSymbol |
       | BROU                | 2480-6990        | www.brou.com.uy             | BROUUY         |
      | SANTANDER    | 2487-7190         | www.santander.com.uy    | SANTUY         |
      | BBVA                | 2327-7182         | www.bbva.com.uy            | BBVAUY         |
      | ITAU                 | 2218-7982         | www.itau.com.uy              | ITAUUY           |
