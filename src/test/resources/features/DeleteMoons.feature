@DeleteMoons
Feature: DeleteMoons

	@SCRUM-TC-31
	Scenario Outline: As a user I want to be able to remove Planets from the Planetarium
	Delete Planet Positive Scenario
		Given The User is already logged in
		Given Moon name "<MoonName>" exist
		When The User selects moon from drop-down menu
		When User enters valid moon name "<MoonName>" for celestial body to be deleted
		When User clicks on the Delete Button
		Then The moon "<MoonName>" should be deleted from the planetarium

	Examples: 
		| MoonName |
		| Moon     |
		| MoonMoon |
		|10001      |
		|10002       |


	@SCRUM-TC-32
	Scenario Outline: As a user I should not be able to remove Moon from the Planetarium when given invalid names
	Delete Moon Negative Scenario
		Given The User is already logged in
		Given There is no Moon named "<invalidMoonNames>" in planetarium
		When The User selects moon from drop-down menu
		When User enters invalid "<InvalidMoonNames>"
		When User clicks on the Delete Button
		Then The user should see error message pop-up

	Examples: 
		| invalidMoonNames   |
		| Kirby of the Stars |
		| Hoshi no Narby     |

	@SCRUM-TC-34
	Scenario: User should not be able to delete a moon by  its ID
		Given The User is already logged in
		Given Moon with ID "<ID>" exists
		When The User selects moon from drop-down menu
		When User enters moon id "<ID>" for celestial body to be deleted
		When User clicks on the Delete Button
		Then The user should see error, and the moon with ID "<ID>" should not be deleted

	Examples:
		| ID |
		| 1  |