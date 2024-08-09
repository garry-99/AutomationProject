package com.revature.step;

import com.revature.TestRunner;
import com.revature.entity.PlanetEntity;
import com.revature.entity.UserEntity;
import com.revature.repositories.PlanetRepository;
import com.revature.repositories.UserRepository;
import com.revature.utilities.DatabaseScriptRunnerUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddPlanetsSteps {

    @Given("The User is already log on.")
    public void the_User_is_already_log_on() {
        // All the steps required to log the user in
        UserEntity userEntity = new UserEntity("Lisan", "al-gaib");
        UserRepository.addUser(userEntity);
        TestRunner.planetariumLoginHome.goToPlanetariumLoginHome();
        TestRunner.planetariumLoginHome.sendToUsernameInput("Lisan");
        TestRunner.planetariumLoginHome.sendToPasswordInput("al-gaib");
        TestRunner.planetariumLoginHome.clickLoginButton();
        TestRunner.wait.until(ExpectedConditions.titleIs("Home"));
    }

    @Given("No planet with name {string} in planetarium")
    public void no_planet_with_name_in_planetarium(String string) {
        // Deletes planet from the planetarium if it already exists
        PlanetEntity planetEntity = new PlanetEntity(string);
        if(string.equals("nonunique")){
            PlanetRepository.addPlanet(planetEntity);
            return;
        }
        PlanetRepository.deletePlanet(planetEntity);
        TestRunner.refresh();
    }

    @When("The User selects planets from the drop-down menu")
    public void the_User_selects_planets_from_the_drop_down_menu() {
        TestRunner.planetariumHome.selectPlanet();
    }

    @When("The User enters {string} for planet name")
    public void the_User_enters_for_planet_name(String string) {
        TestRunner.planetariumHome.sendToPlanetNameInput(string);
    }

    @When("{string} The User selects an image from filesystem for planet image")
    public void the_User_selects_an_image_from_filesystem_for_planet_image(String string) {
        if(string.equals("true")){
            TestRunner.planetariumHome.uploadImage();
        }
    }

    @When("The User clicks on the submit button")
    public void the_User_clicks_on_the_submit_button() {
        TestRunner.planetariumHome.clickSubmitButton();
    }

    @Then("The planet {string} should be added to the planetarium")
    public void the_planet_should_be_added_to_the_planetarium(String string) {
        TestRunner.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("celestialTable")));
        WebElement myElement = new WebDriverWait(TestRunner.driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("td")));
        Assert.assertTrue(TestRunner.planetariumHome.getPlanetName().contains(string));
    }

    @Then("The planet {string} should not be added to the planetarium")
    public void the_planet_should_not_be_added_to_the_planetarium(String string) {
        try{
            TestRunner.wait.until(ExpectedConditions.alertIsPresent());
        }
        catch (TimeoutException e){
            Assert.fail();
        }
        Assert.assertTrue(TestRunner.planetariumHome.isAlertPresent());
        TestRunner.driver.switchTo().alert().accept();

    }
}
