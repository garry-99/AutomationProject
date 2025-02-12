package com.revature.services;

import com.revature.TestRunner;
import com.revature.entity.UserEntity;
import com.revature.repositories.UserRepository;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginService {
    public boolean login(String username, String password){



        TestRunner.planetariumLoginHome.goToPlanetariumLoginHome();
        TestRunner.wait.until(ExpectedConditions.titleIs("Planetarium Login"));

        TestRunner.planetariumLoginHome.sendToUsernameInput(username);
        TestRunner.planetariumLoginHome.sendToPasswordInput(password);

        TestRunner.planetariumLoginHome.clickLoginButton();

        TestRunner.wait.until(ExpectedConditions.titleIs("Home"));

        String planetariumTitle = TestRunner.driver.getTitle();
        return planetariumTitle.equals("Home");
    }

    //logs in as Batman
    public static boolean loginBatman(){
        TestRunner.planetariumLoginHome.goToPlanetariumLoginHome();
        TestRunner.wait.until(ExpectedConditions.titleIs("Planetarium Login"));

        TestRunner.planetariumLoginHome.sendToUsernameInput("Batman");
        TestRunner.planetariumLoginHome.sendToPasswordInput("I am the night");

        TestRunner.planetariumLoginHome.clickLoginButton();

        TestRunner.wait.until(ExpectedConditions.titleIs("Home"));
        String planetariumTitle = TestRunner.driver.getTitle();
        return planetariumTitle.equals("Home");
    }
}
