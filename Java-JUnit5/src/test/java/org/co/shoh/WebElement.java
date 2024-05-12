package org.co.shoh;

import org.co.shoh.StepDefs.TestComponent;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;

public class WebElement {
    public By locator = null;
    public org.openqa.selenium.WebElement webElement = null;
    public int defaultWaitSeconds = 5; //default wait to find all elements
    Wait<WebDriver> wait =
            new FluentWait<>(TestComponent.automationDriver.getWebDriver())
                    .withTimeout(Duration.ofSeconds(defaultWaitSeconds))
                    .pollingEvery(Duration.ofMillis(300))
                    .ignoring(ElementNotInteractableException.class)
                    .ignoring(NoSuchElementException.class);

    public WebElement(By locator) {
        this.locator = locator;
        wait.until(
                element -> {
                    webElement = element.findElement(this.locator);
                    return true;
                });
    }

    public void click() {
        wait.until(
                element -> {
                    webElement.click();
                    return true;
                });
    }

    public void sendKeys(String keys) {

        wait.until(
                element -> {
                    webElement.sendKeys(keys);
                    return true;
                });
    }

    public void waitUntilIsDisplayed() {
        wait.until(
                element -> {
                    webElement.isDisplayed();
                    return true;
                });
    }

    public boolean isDisplayed() {
        wait.until(
                element -> {
                    return webElement.isDisplayed();
                });
        return webElement.isDisplayed();
    }

}
