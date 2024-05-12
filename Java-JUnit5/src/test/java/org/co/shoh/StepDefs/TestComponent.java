package org.co.shoh.StepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.co.shoh.AutomationDriver;
import org.co.shoh.HelperClasses.DateHelper;

public class TestComponent {
    public static AutomationDriver automationDriver = null;
    public static Scenario scenario;


    @Before
    public void before(Scenario scenario)  {
        TestComponent.scenario = scenario;
        automationDriver = new AutomationDriver();
    }


    @After
    public void after() {
        if (automationDriver != null) {
            scenario.log("Timestamp at finish: " + DateHelper.getDateInFormat("HH:mm:ss.SSS"));
            if (scenario.isFailed()) {
                automationDriver.takeFullPageScreenshot(scenario);
            }
            automationDriver.quit();
        }
    }
}
