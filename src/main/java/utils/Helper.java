package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class Helper {

    private WebDriver driver;

    public Helper(WebDriver driver) {
        this.driver = driver;
    }

    public void clickOnElement(String locator, String locatorvalue) {

        if (locator.equalsIgnoreCase("id")) {
            driver.findElement(By.id(locatorvalue)).click();
        }

        if (locator.equalsIgnoreCase("name")) {
            driver.findElement(By.name(locatorvalue)).click();
        }

        if (locator.equalsIgnoreCase("css")) {
            driver.findElement(By.cssSelector(locatorvalue)).click();
        }

        if (locator.equalsIgnoreCase("xpath")) {
            driver.findElement(By.xpath(locatorvalue)).click();
        }

    }
}
