package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Base{

    public Base(WebDriver driver){
        PageFactory.initElements(driver, this);
    }
}
