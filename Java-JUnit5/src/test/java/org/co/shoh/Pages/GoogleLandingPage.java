package org.co.shoh.Pages;

import org.co.shoh.WebElement;
import org.openqa.selenium.By;

public class GoogleLandingPage {
    public WebElement logoImage = new WebElement(By.id("hplogo"));
    public WebElement txtSearch = new WebElement(By.tagName("textarea"));

    public GoogleLandingPage() {
        logoImage.waitUntilIsDisplayed();
    }
}
