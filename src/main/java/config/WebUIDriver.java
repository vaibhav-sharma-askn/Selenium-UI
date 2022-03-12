package config;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class WebUIDriver {

    public static WebDriver driver = null;
    private static final Logger log = LoggerFactory.getLogger(WebUIDriver.class);
    private final static Config config = ConfigFactory.load();



    public synchronized static WebDriver getCurrentDriver() {
        String browser = System.getProperty("browser");
        switch(browser.toLowerCase()){
            case "chrome":
                log.info("Starting Execution on Chrome Browser");
                System.setProperty("webdriver.chrome.driver","src/main/resources/drivers/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "firefox":
                log.info("Starting Execution on Firefox Browser");

                driver = new FirefoxDriver();
                break;
            case "headless":
                log.info("Starting Execution on Chrome Browser in headless mode");
                ChromeOptions options = new ChromeOptions();
                options.addArguments("headless");
                options.addArguments("window-size=1200x600");
                driver = new ChromeDriver(options);
                break;
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        launchApplication();
        return driver;
    }

    public static void launchApplication(){
        driver.get(config.getString("url"));
    }

    public void closeDriverSession(){
        driver.quit();
    }
}
