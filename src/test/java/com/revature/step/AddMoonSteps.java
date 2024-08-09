package com.revature.step;

import com.revature.TestRunner;
import com.revature.entity.MoonEntity;
import com.revature.entity.PlanetEntity;
import com.revature.entity.UserEntity;
import com.revature.repositories.MoonRepository;
import com.revature.repositories.PlanetRepository;
import com.revature.services.LoginService;
import com.revature.utilities.DatabaseScriptRunnerUtility;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.revature.pom.PlanetariumHome;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AddMoonSteps {

    @Given("The User is already log on")
    public void the_User_is_already_log_on_with_thanh_username() {
        // All the steps required to log the user in
        TestRunner.planetariumLoginHome.goToPlanetariumLoginHome();
        TestRunner.planetariumLoginHome.sendToUsernameInput("thanh");
        TestRunner.planetariumLoginHome.sendToPasswordInput("123");
        TestRunner.planetariumLoginHome.clickLoginButton();
        TestRunner.wait.until(ExpectedConditions.titleIs("Home"));
    }

    @Given("No Planet or Moon with name {string} in planetarium")
    public void no_planet_or_moon_with_name_in_planetarium(String string) {
        // Deletes planet/moon from the planetarium if it already exists
        PlanetEntity planetEntity = new PlanetEntity(string);
        MoonEntity moonEntity = new MoonEntity(string);
        PlanetRepository.deletePlanet(planetEntity);
        MoonRepository.deleteMoon(moonEntity);
        TestRunner.refresh();

    }
    @Given("The ID of the Planet {string} that the moon orbiting does exist in Planetarium")
    public void moon_orbiting_planet_exists(String string) {
        boolean exists = TestRunner.planetariumHome.doesPlanetExist(string);
        Assert.assertTrue("Planet with ID " + string + " should exist.", exists);
    }

//    @Given("The ID of the Planet {string} does not exist in the Planetarium")
//    public void id_of_orbited_planet_does_not_exist(String string) {
//        boolean exists = TestRunner.planetariumHome.doesPlanetExist(string);
//        Assert.assertFalse("Planet with ID " + string + " should exist.", exists);
//    }



    @When("The User selects moon from the drop-down menu")
    public void the_User_selects_moon_from_the_drop_down_menu() {
        TestRunner.planetariumHome.selectMoon();
    }

    @When("The User enters {string} for moon name")
    public void the_User_enters_for_moon_name(String string) {
        TestRunner.planetariumHome.sendToMoonNameInput(string);
    }

    @When("The User enters {string} for the planet that the moon is orbiting")
    public void the_User_enters_orbited_planet_id(String string) {
        TestRunner.planetariumHome.sendToOrbitedPlanetInput(string);
    }

    @When("{string} The User selects an image from file explorer for moon image")
    public void the_User_selects_an_image_from_filesystem_for_moon_image(String image) {
        if (!image.isEmpty()) {
            TestRunner.planetariumHome.uploadMoonImage();
        }
    }


    @Then("The Moon {string} should be added to planetarium")
    public void the_moon_should_be_added_to_the_planetarium(String string) {
        TestRunner.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("celestialTable")));
        WebElement myElement = new WebDriverWait(TestRunner.driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("celestialTable")));
        Assert.assertTrue(TestRunner.planetariumHome.getPlanetName().contains(string));
    }


    @Then("The Moon {string} should not be added to planetarium")
    public void the_moon_should_not_be_added_to_the_planetarium(String string) {
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
