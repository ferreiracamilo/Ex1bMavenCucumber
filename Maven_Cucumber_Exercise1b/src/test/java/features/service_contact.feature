#Author: Camilo Ferreira
Feature: App > Service App > Contact
  It will be tested all features related to this functionality

  Background: Contact functionality background
    Given Initialize the browser with "chrome"
    And User navigates to "https://login.salesforce.com" Site
    And User enters credentials and logs in
    And User clicks on waffle menu button
    And User clicks on Service button
    And User clicks on Contacts tab

  #Exercise 1b - 4
  #Will be erased contact records as 1st step
  #Due to mandatory field and extra info check entries for fields on below comment
  #Mailing Street as 18 de julio, 1st Name as Rodolfo, Salutation as Mr., Lastname as Caceres
  Scenario: Create contact record using account name from previous account created
    Given User erase all respective records
    And User clicks on Contacts creating a new tab
    And User goes to new tab
    And User clicks on New button
    And User select within Contact form account name field as previous account record -> "1stAccount"
    And User Clicks on Save button
    When System launchs sucess notification
    And User goes back to initial tab
    Then Close browser