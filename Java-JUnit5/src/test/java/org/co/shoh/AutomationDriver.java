package org.co.shoh;

import io.appium.java_client.windows.WindowsDriver;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.co.shoh.HelperClasses.PropertyHelper;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * The {@code Driver} class represents the setup for the test execution.
 * This Driver class allows computers and mobile devices to be executed with Selenium and Appium.
 * The test is determined via {@code SetupProperty.property} file.
 **/

public class AutomationDriver {

    private WebDriver automationDriver;

    public String deviceTarget;
    public String browserTarget;
    public String appTarget;
    public String env;
    // For Appium applications use
    final private String appiumURL = "http://127.0.0.1:4723/wd/hub";
    final private String windowsDriverURL = "http://127.0.0.1:4723/";

    public static ExecutionData executionData;
    public static Properties setupProperty;

    public AutomationDriver() {
        setup();
    }

    public void setup() {
        executionData = new ExecutionData();
        // loads setup property file
        setupProperty = PropertyHelper.loadProperties("src/main/resources/setupProperty.properties");

        // from setupFile loads which device test is to run against at
        deviceTarget = setupProperty.getProperty("deviceTarget");

        //sets the environment
        env = setupProperty.getProperty("env");

        assert deviceTarget != null;
        if (deviceTarget.equalsIgnoreCase("default")) {
            setupDefaultTestDriver();
        } else if (deviceTarget.equalsIgnoreCase("ios") || deviceTarget.equalsIgnoreCase("android")) {
            try {
                setupMobileTestDriver(deviceTarget);
            } catch (URISyntaxException | MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else {
            fail("Invalid deviceTarget in setupProperty.property file.");
        }
    }

    private void setupDefaultTestDriver() {
        browserTarget = setupProperty.getProperty("browserTarget");
        if (browserTarget.equalsIgnoreCase("chrome")) {
            automationDriver = new ChromeDriver();
        }

        // Chrome option manually in place if needed can be in txt file
        if (browserTarget.equalsIgnoreCase("chrome-options")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            automationDriver = new ChromeDriver(options);
        }
        if (browserTarget.equalsIgnoreCase("firefox")) {
            automationDriver = new FirefoxDriver();
        }

        // Mac requires 'Allow Remote Automation' setting to be turned on
        if (browserTarget.equalsIgnoreCase("safari")) {
            automationDriver = new SafariDriver();
        }

        if (browserTarget.equalsIgnoreCase("edge")) {
            automationDriver = new EdgeDriver();
        }

        if (browserTarget.equalsIgnoreCase("ie")) {
            automationDriver = new InternetExplorerDriver();
        }

        // Execution machine must have WinAppDriver installed
        if (browserTarget.equalsIgnoreCase("winapp")) {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("platformName", "Windows");
            caps.setCapability("deviceName", "WindowsPC");
            caps.setCapability("app", appTarget); //make sure this is added to the global.properties
            try {
                URI uri = new URI(windowsDriverURL);
                automationDriver = new WindowsDriver(uri.toURL(), caps);
            } catch (MalformedURLException | URISyntaxException e) {
                e.printStackTrace();
            }

        }
    }

    private void setupMobileTestDriver(String deviceOS) throws URISyntaxException, MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        URI uri = new URI(appiumURL);
        Properties mobileCapabilities = PropertyHelper.loadProperties("src/main/resources/" + deviceOS + "Capabilities.properties");
        String deviceName = mobileCapabilities.getProperty("deviceName");
        if (setupProperty.getProperty("isEmulator").equalsIgnoreCase("true")) {
            startADBEmulator(deviceName);
        }
        for (Object key : mobileCapabilities.keySet()) {
            if (!mobileCapabilities.getProperty((String) key).isEmpty()) {
                caps.setCapability(key.toString(), mobileCapabilities.getProperty(key.toString()));
            }

        }
    }

    private void startADBEmulator(String deviceName) {
        System.out.println("\t\tAttempting to start ADB emulator");
        Process process = null;
        try {
            process = new ProcessBuilder(System.getProperty("user.home") + "/Library/Android/sdk/emulator/emulator -avd " + deviceName.replace(" ", "_") + " -netdelay none -netspeed full").start();
            int exitCode = process.waitFor();
            assertEquals(0, exitCode, "No errors should be detected");

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void takeFullPageScreenshot(Scenario scenario) {
        File logdir = new File("target/cucumberHTML");
        File path = new File(logdir, "selenium-screenshot.png");

        try {
            File screenShotFile = ((TakesScreenshot) automationDriver).getScreenshotAs(OutputType.FILE);
            BufferedImage bufferedImage = ImageIO.read(screenShotFile);
            writeScreenshot(path, bufferedImage, scenario);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void navigateTo(String URL) {
        automationDriver.navigate().to(URL);
    }

    public void quit() {
        automationDriver.quit();
    }

    public WebDriver getWebDriver() {
        return automationDriver;
    }

    protected void writeScreenshot(File path, BufferedImage img, Scenario scenario) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            ImageIO.write(img, "PNG", fos);
            fos.flush();
            byte[] imageInByte = FileUtils.readFileToByteArray(path);
            scenario.attach(imageInByte, "image/png", "");
        } catch (IOException e) {
            System.out.println(String.format("Can't write screenshot '%s'", path.getAbsolutePath()));
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    System.out.println("Can't even close stream.");
                }
            }
            path.delete();
        }
    }
}
