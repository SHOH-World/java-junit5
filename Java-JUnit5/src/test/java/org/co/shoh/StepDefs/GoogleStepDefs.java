package org.co.shoh.StepDefs;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.co.shoh.Pages.GoogleLandingPage;
import org.co.shoh.Pages.GoogleSearchPage;
import org.openqa.selenium.Keys;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoogleStepDefs {

    @When("^I search for (.*) in the search box$")
    public void iSearchForInTheSearchBox(String searchWord) {
        GoogleLandingPage googleLandingPage = new GoogleLandingPage();
        googleLandingPage.txtSearch.sendKeys("hello" + Keys.ENTER);
    }

    @Then("I see the search result")
    public void iSeeTheSearchResult() {
        GoogleSearchPage googleSearchPage = new GoogleSearchPage();
        assertTrue(googleSearchPage.searchResult.isDisplayed(), "Search Result must be displayed.");

    }
}
