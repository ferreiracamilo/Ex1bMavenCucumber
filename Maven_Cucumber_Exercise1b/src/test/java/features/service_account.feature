#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: App > Service App > Accounts
  It will be tested all features related to this functionality

Background:
	Given Initialize the browser with "chrome" 
	And User navigates to "https://login.salesforce.com" Site
	And User enters credentials and logs in
	And User clicks on waffle menu button and 
	And User clicks on Service button
	
#Create account record by filling all fields (values will be provided within java class).
#Additional requirement: value for SLA Expiration Date must be clicked in calendar.
#Will be erase every account before running 1st scenario
#Exercise 1b - 2
Scenario: Create Account with full fields
	Given User clicks on "Accounts" tab
	When Erase All Account Records
	And User clicks on "New" button
	And User inputs "1stAccount" as AccountName and fill rest of the form
	And User Clicks on "Save" button
	And Close browser

#Verify error launches when Account Record is attemp to be created without filling mandatory field (Account Name)
#Exercise 1b - 3
Scenario: Negative create account record outcome due to void accountname
	Given User clicks on "Accounts" tab
	And User clicks on "New" button
	And User Clicks on "Save" button
	Then Launch error by displaying a stop sign icon
	And Close browser

#Exercise 1b - 4
Scenario: Create account using account name from previous account created
	Given User clicks on "Accounts" tab
	And User save name of previous account record
	And User clicks on "Contacts" creating a new tab 
	And User goes to new tab
	And User clicks on "New" button
	And User inputs value retrieved as AccountName
	And User Clicks on "Save" button
	And User goes back to initial tab
	And Close browser

#Fields to modified are Rating, Upsell Opportunity, Type
#Exercise 1b - 5
  Scenario: Modify values from account record
    Given User clicks on "Accounts" tab
    And User click menu (arrow down) and click "Edit" button from row which contains "1stAccount"
    And User modify "Rating" to "Cold", "Upsell Opportunity" to "Yes" and "Type" to "Other" dropdown options
    And User Clicks on "Save" button
    When Sucess notification appears
    Then Close Browser

#Field to modified is Employee
#Exercise 1b - 6
  Scenario: Modify values from account record
    Given User clicks on "Accounts" tab
    And User click menu (arrow down) and click "Edit" button from row which contains "1stAccount"
    And User modify "Employees" input field to "1431655766."
    And User Clicks on "Save" button
    When warning message "Employees: value outside of valid range on numeric field: 1431655766" is displayed
    Then Close Browser

#Exercise did not specify to fill the complete form, therefore dataprovider will contain only few possible fields
#Exercise 1b - 7
  Scenario Outline: Create multiple account with DataProvider
    Given User clicks on "Accounts" tab
		When Erase All Account Records
		And User clicks on "New" button
		And User inputs <accountName> into Account Name input
		And User inputs <phoneNumber> into Phone input
		And User inputs <websiteName> into Website input
		And User inputs <tyckerSymbol> into Tycker Symbol input
		And User Clicks on "Save" button
		And Close browser
    
    Examples: 
      | accountName   | phoneNumber | websiteName                    | tyckerSymbol |
      | BROU                | 2480-6990        | www.brou.com.uy             | BROUUY         |
      | SANTANDER    | 2487-7190         | www.santander.com.uy    | SANTUY         |
      | BBVA                | 2327-7182         | www.bbva.com.uy            | BBVAUY         |
      | ITAU                 | 2218-7982         | www.itau.com.uy              | ITAUUY           |
