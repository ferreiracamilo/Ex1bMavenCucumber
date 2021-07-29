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
Feature:  App > Service App
   It will be tested all features related to this functionality

 Background:
	Given Initialize the browser with "chrome" 
	And User navigates to "https://login.salesforce.com" Site
	And User enters credentials and logs in

#Exercise 1
#Additional requirement: Click new and cancel if applies for tab
	Scenario: Go through app tabs
	Given User clicks on waffle menu button
	And User clicks on Service button
	And User click every tab (click new and cancel if applies)
	Then Close browser