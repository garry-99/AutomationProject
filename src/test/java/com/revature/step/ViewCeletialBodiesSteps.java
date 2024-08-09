package com.revature.step;

import com.revature.TestRunner;
import com.revature.entity.MoonEntity;
import com.revature.entity.PlanetEntity;
import com.revature.entity.UserEntity;
import com.revature.repositories.MoonRepository;
import com.revature.repositories.PlanetRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.LoginService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.After;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ViewCeletialBodiesSteps {

    private LoginService loginService;
    private UserEntity   testUser;
    public ViewCeletialBodiesSteps(){
        this.loginService = new LoginService();
    }

    @Given("The User is already logged in with {string} and  {string}")
    public void the_User_is_already_logged_in_with_and(String username, String password) {
        testUser = new UserEntity(username, password);
        UserRepository.addUser(testUser);

        boolean checkLog = loginService.login(username, password);
        Assert.assertTrue(username + "is logged in", checkLog);
    }

    @Then("User is redirected to the Planetarium")
        public void the_User_is_redirected_to_the_Planetarium() {
            // Write code here that turns the phrase above into concrete actions
            TestRunner.wait.until(ExpectedConditions.titleIs("Home"));
            Assert.assertEquals("Home", TestRunner.driver.getTitle());
        }
    @Then("User see all the available celestial Bodies.")
        public void user_sees_all_the_bodies(){
        Map<String, List<Integer>> tableData  = TestRunner.planetariumHome.viewAllData();

//        Select id from planets and moons and compare them with table with the data from table

        List<Integer> planetIds = PlanetRepository.getPlanets();
        List<Integer> moonIds = MoonRepository.getMoons();
        System.out.println(tableData);
        System.out.println(planetIds);
        System.out.println(moonIds);


        boolean isMoonSame = moonIds.equals(tableData.getOrDefault("moon", new ArrayList<>()));
        boolean isPlanetSame = planetIds.equals(tableData.getOrDefault("planet",new ArrayList<>()));

        Assert.assertTrue(isMoonSame && isPlanetSame);
        }

    @After
    public void tearDown(){
        if(testUser != null){
            UserRepository.deleteUser(testUser);
        }
    }
}
