package config;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    WebUIDriver webUIDriver = new WebUIDriver();

    @Before
    public void initializeTest() {
        webUIDriver.getCurrentDriver();
    }

    @After
    public void closeSession() {
        webUIDriver.closeDriverSession();
    }
}
