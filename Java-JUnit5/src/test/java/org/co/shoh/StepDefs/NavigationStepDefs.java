package org.co.shoh.StepDefs;

import io.cucumber.java.en.When;

public class NavigationStepDefs {
    @When("^I navigate to (.*)$")
    public void i_navigate_to_(String URL) {
        TestComponent.automationDriver.navigateTo(URL);
    }
}
