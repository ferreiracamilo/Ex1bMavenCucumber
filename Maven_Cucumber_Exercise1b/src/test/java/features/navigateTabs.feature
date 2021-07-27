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

Feature: Navigate all tabs and click buttons (New & Cancel) within tabs

Background:
	Given Initialize the browser with "chrome"
	And User navigates to "https://login.salesforce.com" Site
	And User enters credentials and logs in
	
Scenario:
	Given User clicks on waffle menu button
	And User clicks on "Service" button
	When User clicks on every tab
	And User clicks on every new and cancel button
	Then Close browser