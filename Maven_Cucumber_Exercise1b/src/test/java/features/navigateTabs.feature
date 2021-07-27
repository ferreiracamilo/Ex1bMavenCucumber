#Browser Available are
#chrome,firefox,opera

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