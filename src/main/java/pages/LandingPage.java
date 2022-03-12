package pages;

import config.Base;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LandingPage extends Base {

    @FindBy(id = "inventory_container")
    public WebElement inventoryListing;

    public LandingPage(WebDriver driver) {
        super(driver);
    }


    public boolean landingPageDisplayed(){
        try{
           return inventoryListing.isDisplayed();
        }catch (Exception e){
            return false;
        }
    }
}
