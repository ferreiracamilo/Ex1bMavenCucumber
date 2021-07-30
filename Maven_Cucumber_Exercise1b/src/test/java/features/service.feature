#Author: Camilo Ferreira

Feature: App > Service App
  It will be tested all features related to this functionality

Background: Service App functionality background
	Given Initialize the browser with "chrome" 
	And User navigates to "https://login.salesforce.com" Site
	And User enters credentials and logs in
	And User clicks on waffle menu button 
	And User clicks on Service button
	
#Exercise 1
#Additional requirement: Click new and cancel if applies for tab
	Scenario: Go through app tabs
	Given User clicks every tab (click new and cancel if applies)	
	Then Close browser